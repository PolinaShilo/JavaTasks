CREATE DATABASE IF NOT EXISTS phonebook_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE phonebook_db;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    full_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    email_confirmed BOOLEAN DEFAULT FALSE,
    telegram_chat_id VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS contacts (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        user_id BIGINT NOT NULL,
                                        name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_email ON users(email);