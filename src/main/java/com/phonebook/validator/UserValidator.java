package com.phonebook.validator;
import com.phonebook.exception.PhoneBookException;

import java.util.regex.Pattern;

public final class UserValidator {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");

    private UserValidator() {}

    public static void validateUsername(String username) throws PhoneBookException {
        if (username == null || !USERNAME_PATTERN.matcher(username).matches()) {
            throw new PhoneBookException("Invalid username (3-20 chars, letters/digits/_)");
        }
    }

    public static void validatePassword(String password) throws PhoneBookException {
        if (password == null || password.length() < 4) {
            throw new PhoneBookException("Password must be at least 4 characters");
        }

    }

    public static void validateEmail(String email) throws PhoneBookException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new PhoneBookException("Invalid email format");
        }
    }
}