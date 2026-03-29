package com.news.newsLetter.user.security;

import com.news.newsLetter.infra.PasswordEncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptionServiceTest {

    private PasswordEncryptionService passwordEncryptionService;
    private String rawPassword;

    @BeforeEach
    void setUp() {
        passwordEncryptionService = new PasswordEncryptionService();
        rawPassword = "password123";
    }

    @Test
    void encrypt__should_encrypt_password_successfully() {
        // Act
        String encryptedPassword = passwordEncryptionService.encrypt(rawPassword);

        // Assert
        assertNotNull(encryptedPassword);
        assertNotEquals(rawPassword, encryptedPassword);
        assertTrue(encryptedPassword.startsWith("$2a$") || encryptedPassword.startsWith("$2b$"));
    }

    @Test
    void encrypt__should_generate_different_hashes_for_same_password() {
        // Act
        String encryptedPassword1 = passwordEncryptionService.encrypt(rawPassword);
        String encryptedPassword2 = passwordEncryptionService.encrypt(rawPassword);

        // Assert
        assertNotNull(encryptedPassword1);
        assertNotNull(encryptedPassword2);
        assertNotEquals(encryptedPassword1, encryptedPassword2);
    }

    @Test
    void matches__should_return_true_when_password_matches() {
        // Arrange
        String encryptedPassword = passwordEncryptionService.encrypt(rawPassword);

        // Act
        boolean result = passwordEncryptionService.matches(rawPassword, encryptedPassword);

        // Assert
        assertTrue(result);
    }

    @Test
    void matches__should_return_false_when_password_does_not_match() {
        // Arrange
        String encryptedPassword = passwordEncryptionService.encrypt(rawPassword);
        String wrongPassword = "wrongPassword123";

        // Act
        boolean result = passwordEncryptionService.matches(wrongPassword, encryptedPassword);

        // Assert
        assertFalse(result);
    }

    @Test
    void encrypt__should_not_return_empty_string() {
        // Act
        String encryptedPassword = passwordEncryptionService.encrypt(rawPassword);

        // Assert
        assertNotNull(encryptedPassword);
        assertFalse(encryptedPassword.isEmpty());
        assertTrue(encryptedPassword.length() > rawPassword.length());
    }
}
