package com.phonebook.dao;

import com.phonebook.entity.User;
import com.phonebook.util.PasswordUtil;
import com.phonebook.exception.PhoneBookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.phonebook.connection.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
    private final ConnectionPool pool;

    public UserDaoImpl(ConnectionPool pool) { this.pool = pool; }

    @Override
    public User save(User user, String rawPassword) throws PhoneBookException {
        String sql = "INSERT INTO users (username,password,email,phone_number,full_name,enabled) VALUES (?,?,?,?,?,?)";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getUsername());
            st.setString(2, PasswordUtil.hash(rawPassword));
            st.setString(3, user.getEmail());
            st.setString(4, user.getPhoneNumber());
            st.setString(5, user.getFullName());
            st.setBoolean(6, true);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    return new User.Builder()
                            .id(rs.getLong(1))
                            .username(user.getUsername())
                            .password(PasswordUtil.hash(rawPassword))
                            .email(user.getEmail())
                            .phoneNumber(user.getPhoneNumber())
                            .fullName(user.getFullName())
                            .enabled(true)
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to save user", e);
        }
        throw new PhoneBookException("Failed to save user: no ID generated");
    }

    @Override
    public Optional<User> findByUsername(String username) throws PhoneBookException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to find user", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) throws PhoneBookException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to find user by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws PhoneBookException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = pool.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to list users", e);
        }
        return list;
    }

    @Override
    public User update(User user) throws PhoneBookException {
        String sql = "UPDATE users SET username=?, email=?, phone_number=?, full_name=? WHERE id=?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, user.getUsername());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getFullName());
            st.setLong(5, user.getId());
            st.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to update user", e);
        }
    }

    @Override
    public void delete(Long id) throws PhoneBookException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to delete user", e);
        }
    }

    @Override
    public boolean existsByUsername(String username) throws PhoneBookException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to check username", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) throws PhoneBookException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to check email", e);
        }
    }

    @Override
    public void confirmEmail(String email) throws PhoneBookException {
        String sql = "UPDATE users SET email_confirmed = TRUE WHERE email = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, email);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to confirm email", e);
        }
    }

    @Override
    public void updateTelegramChatId(String username, String chatId) throws PhoneBookException {
        String sql = "UPDATE users SET telegram_chat_id = ? WHERE username = ?";
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, chatId);
            st.setString(2, username);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new PhoneBookException("Failed to update telegram chat id", e);
        }
    }

    private User map(ResultSet rs) throws SQLException {
        return new User.Builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .fullName(rs.getString("full_name"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .enabled(rs.getBoolean("enabled"))
                .emailConfirmed(rs.getBoolean("email_confirmed"))
                .telegramChatId(rs.getString("telegram_chat_id"))
                .build();
    }
}