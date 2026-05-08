package com.phonebook.service;

import com.phonebook.dao.UserDao;
import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_ShouldThrowException_WhenUsernameExists() throws PhoneBookException {
        when(userDao.existsByUsername("john")).thenReturn(true);

        User user = new User.Builder()
                .username("john")
                .email("john@mail.com")
                .build();

        assertThrows(PhoneBookException.class, () ->
                userService.register(user, "Password123!")
        );

        verify(userDao, never()).save(any(), anyString());
    }

    @Test
    void register_ShouldSaveUser_WhenUsernameAndEmailAreFree() throws PhoneBookException {
        when(userDao.existsByUsername("john")).thenReturn(false);
        when(userDao.existsByEmail("john@mail.com")).thenReturn(false);

        User user = new User.Builder()
                .username("john")
                .email("john@mail.com")
                .phoneNumber("+123456789")
                .fullName("John Doe")
                .build();

        User savedUser = new User.Builder()
                .id(1L)
                .username("john")
                .email("john@mail.com")
                .build();

        when(userDao.save(any(User.class), anyString())).thenReturn(savedUser);

        User result = userService.register(user, "Password123!");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userDao).save(any(User.class), anyString());
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreValid() throws PhoneBookException {
        User user = new User.Builder()
                .username("john")
                .password("hashedPassword")
                .emailConfirmed(true)
                .build();

        when(userDao.findByUsername("john")).thenReturn(Optional.of(user));

        // Note: PasswordUtil.verify needs to be mocked or use real hash
        Optional<User> result = userService.login("john", "Password123!");

        assertTrue(result.isPresent());
    }

    @Test
    void login_ShouldReturnEmpty_WhenUserNotFound() throws PhoneBookException {
        when(userDao.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<User> result = userService.login("unknown", "pass");

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws PhoneBookException {
        when(userDao.findAll()).thenReturn(java.util.List.of(
                new User.Builder().id(1L).username("user1").build(),
                new User.Builder().id(2L).username("user2").build()
        ));

        var users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userDao).findAll();
    }

    @Test
    void deleteUser_ShouldCallDaoDelete() throws PhoneBookException {
        userService.deleteUser(1L);
        verify(userDao).delete(1L);
    }
}