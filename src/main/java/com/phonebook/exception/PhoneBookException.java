package com.phonebook.exception;

public class PhoneBookException extends Exception {

    public PhoneBookException(String message) {
        super(message);
    }

    public PhoneBookException(String message, Throwable cause) {
        super(message, cause);
    }
}