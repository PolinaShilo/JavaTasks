package com.phonebook.service;

import com.phonebook.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void hashPassword_ShouldReturnNonNull() {
        String hash = PasswordUtil.hash("myPassword123!");
        assertNotNull(hash);
        assertTrue(hash.contains(":"));
    }

    @Test
    void verifyPassword_ShouldReturnTrue_ForValidPassword() {
        String password = "SecurePass123!";
        String hash = PasswordUtil.hash(password);
        assertTrue(PasswordUtil.verify(password, hash));
    }

    @Test
    void verifyPassword_ShouldReturnFalse_ForInvalidPassword() {
        String hash = PasswordUtil.hash("CorrectPass123!");
        assertFalse(PasswordUtil.verify("WrongPass123!", hash));
    }

    @Test
    void hashPassword_ShouldProduceDifferentHashes_ForSamePassword() {
        String password = "SamePassword123!";
        String hash1 = PasswordUtil.hash(password);
        String hash2 = PasswordUtil.hash(password);
        assertNotEquals(hash1, hash2); // разные соли = разные хеши
    }
}