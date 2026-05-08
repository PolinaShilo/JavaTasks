package com.phonebook.entity;

import java.time.LocalDateTime;

public class User {
    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String phoneNumber;
    private final String fullName;
    private final LocalDateTime createdAt;
    private final boolean enabled;
    private final boolean emailConfirmed;
    private final String telegramChatId;

    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.fullName = builder.fullName;
        this.createdAt = builder.createdAt;
        this.enabled = builder.enabled;
        this.emailConfirmed = builder.emailConfirmed;
        this.telegramChatId = builder.telegramChatId;
    }

    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private String email;
        private String phoneNumber;
        private String fullName;
        private LocalDateTime createdAt;
        private boolean enabled = true;
        private boolean emailConfirmed = false;
        private String telegramChatId;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder enabled(boolean enabled) { this.enabled = enabled; return this; }
        public Builder emailConfirmed(boolean emailConfirmed) { this.emailConfirmed = emailConfirmed; return this; }
        public Builder telegramChatId(String telegramChatId) { this.telegramChatId = telegramChatId; return this; }

        public User build() { return new User(this); }
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFullName() { return fullName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isEnabled() { return enabled; }
    public boolean isEmailConfirmed() { return emailConfirmed; }
    public String getTelegramChatId() { return telegramChatId; }
}