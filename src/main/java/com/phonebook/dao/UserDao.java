package com.phonebook.dao;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User save(User user, String rawPassword) throws PhoneBookException;
    Optional<User> findByUsername(String username) throws PhoneBookException;
    Optional<User> findByEmail(String email) throws PhoneBookException;
    List<User> findAll() throws PhoneBookException;
    User update(User user) throws PhoneBookException;
    void delete(Long id) throws PhoneBookException;
    boolean existsByUsername(String username) throws PhoneBookException;
    boolean existsByEmail(String email) throws PhoneBookException;
    void confirmEmail(String email) throws PhoneBookException;
    void updateTelegramChatId(String username, String chatId) throws PhoneBookException;
}