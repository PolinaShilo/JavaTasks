package com.phonebook.service;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.dao.UserDao;

import com.phonebook.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) { this.userDao = userDao; }

    @Override
    public User register(User user, String rawPassword) throws PhoneBookException {
        if (userDao.existsByUsername(user.getUsername())) {
            throw new PhoneBookException("Username already exists");
        }
        if (userDao.existsByEmail(user.getEmail())) {
            throw new PhoneBookException("Email already exists");
        }
        return userDao.save(user, rawPassword);
    }

    @Override
    public Optional<User> login(String username, String password) throws PhoneBookException {
        Optional<User> opt = userDao.findByUsername(username);
        if (opt.isPresent() && PasswordUtil.verify(password, opt.get().getPassword())) {
            return opt;
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() throws PhoneBookException {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(Long id) throws PhoneBookException {
        userDao.delete(id);
    }

    @Override
    public User updateProfile(User user) throws PhoneBookException {
        return userDao.update(user);
    }

    @Override
    public void confirmEmail(String email) throws PhoneBookException {
        userDao.confirmEmail(email);
    }

    @Override
    public void saveTelegramChatId(String username, String chatId) throws PhoneBookException {
        userDao.updateTelegramChatId(username, chatId);
    }

    @Override
    public Optional<User> findByUsername(String username) throws PhoneBookException {
        return userDao.findByUsername(username);
    }
}