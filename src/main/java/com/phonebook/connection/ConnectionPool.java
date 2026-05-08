package com.phonebook.connection;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.config.DatabaseConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private static final ReentrantLock lock = new ReentrantLock();

    private final Queue<Connection> available = new ArrayDeque<>();
    private final String url;
    private final String user;
    private final String pass;
    private final int maxSize;

    private ConnectionPool(String url, String user, String pass, int maxSize) throws PhoneBookException {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.maxSize = maxSize;
        try {
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < maxSize; i++) {
                available.offer(createConnection());
            }
            log.info("Connection pool initialized with size {}", maxSize);
        } catch (ClassNotFoundException | SQLException e) {
            throw new PhoneBookException("Failed to initialize connection pool", e);
        }
    }

    public static void init() throws PhoneBookException {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool(
                            DatabaseConfig.URL,
                            DatabaseConfig.USERNAME,
                            DatabaseConfig.PASSWORD,
                            DatabaseConfig.POOL_SIZE
                    );
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ConnectionPool not initialized. Call init() first.");
        }
        return instance;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public Connection getConnection() throws PhoneBookException {
        lock.lock();
        try {
            Connection conn = available.poll();
            if (conn == null || conn.isClosed()) {
                conn = createConnection();
            }
            log.debug("Connection acquired, remaining: {}", available.size());
            return new ConnectionProxy(conn, this);
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to get connection", e);
        } finally {
            lock.unlock();
        }
    }

    void release(Connection conn) {
        lock.lock();
        try {
            if (conn != null && !conn.isClosed()) {
                available.offer(conn);
            } else {
                available.offer(createConnection());
            }
            log.debug("Connection released, available: {}", available.size());
        } catch (SQLException e) {
            log.error("Failed to release connection", e);
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() {
        lock.lock();
        try {
            Connection conn;
            while ((conn = available.poll()) != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
            log.info("Connection pool shut down");
        } finally {
            lock.unlock();
        }
    }
}