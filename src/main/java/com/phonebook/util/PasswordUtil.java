package com.phonebook.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {
    private static final int SALT_LEN = 16;
    private static final int ITERATIONS = 1000;

    private PasswordUtil() {}

    public static String hash(String password) {
        try {
            byte[] salt = new byte[SALT_LEN];
            new SecureRandom().nextBytes(salt);
            byte[] hash = hashWithSalt(password, salt);
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);
            byte[] computed = hashWithSalt(password, salt);
            return MessageDigest.isEqual(hash, computed);
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] hashWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hash = md.digest(password.getBytes());
        for (int i = 0; i < ITERATIONS; i++) {
            hash = md.digest(hash);
        }
        return hash;
    }
}