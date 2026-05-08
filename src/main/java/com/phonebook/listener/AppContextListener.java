package com.phonebook.listener;

import com.phonebook.connection.ConnectionPool;
import com.phonebook.dao.UserDao;
import com.phonebook.dao.UserDaoImpl;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import com.phonebook.service.UserServiceImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            log.info("Initializing application...");

            // Инициализация Connection Pool
            ConnectionPool.init();
            log.info("Connection pool initialized");

            // Инициализация DAO и Service
            UserDao userDao = new UserDaoImpl(ConnectionPool.getInstance());
            UserService userService = new UserServiceImpl(userDao);

            // Сохранение в контексте приложения
            sce.getServletContext().setAttribute("userService", userService);

            log.info("Application started successfully");

        } catch (PhoneBookException e) {
            log.error("Failed to start application", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Shutting down application...");

        // Удаляем атрибуты из контекста
        sce.getServletContext().removeAttribute("userService");

        // Закрываем Connection Pool
        ConnectionPool.getInstance().shutdown();

        log.info("Application shut down successfully");
    }
}