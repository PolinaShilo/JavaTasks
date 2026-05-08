package com.phonebook.service;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user, String rawPassword) throws PhoneBookException;
    Optional<User> login(String username, String password) throws PhoneBookException;
    List<User> getAllUsers() throws PhoneBookException;
    void deleteUser(Long id) throws PhoneBookException;
    User updateProfile(User user) throws PhoneBookException;
    void confirmEmail(String email) throws PhoneBookException;
    void saveTelegramChatId(String username, String chatId) throws PhoneBookException, PhoneBookException;
    Optional<User> findByUsername(String username) throws PhoneBookException;
}