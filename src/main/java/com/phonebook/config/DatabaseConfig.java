package com.phonebook.config;

public final class DatabaseConfig {
    public static final String URL = "jdbc:postgresql://localhost:5432/phonebook_db";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "admin";  // ← ваш новый пароль
    public static final int POOL_SIZE = 10;

    private DatabaseConfig() {}
}