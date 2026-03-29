package com.news.newsLetter.user.mapper;

import com.news.newsLetter.user.User;
import com.news.newsLetter.user.dto.CreateUserRequest;
import com.news.newsLetter.user.dto.UserResponse;
import com.news.newsLetter.infra.PasswordEncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private PasswordEncryptionService passwordEncryptionService;

    @InjectMocks
    private UserMapper userMapper;

    private CreateUserRequest createUserRequest;
    private User user;
    private String encryptedPassword;

    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest("John Doe", "john@email.com", "password123");
        encryptedPassword = "$2a$10$encryptedPassword123";
        
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@email.com");
        user.setPassword(encryptedPassword);
        user.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void toEntity__should_convert_request_to_user_entity() {
        // Arrange
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);

        // Act
        User result = userMapper.toEntity(createUserRequest);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@email.com", result.getEmail());
        assertEquals(encryptedPassword, result.getPassword());
        verify(passwordEncryptionService, times(1)).encrypt("password123");
    }

    @Test
    void toEntity__should_encrypt_password_during_conversion() {
        // Arrange
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);

        // Act
        User result = userMapper.toEntity(createUserRequest);

        // Assert
        assertNotNull(result.getPassword());
        assertNotEquals("password123", result.getPassword());
        assertEquals(encryptedPassword, result.getPassword());
    }

    @Test
    void toResponse__should_convert_user_to_response() {
        // Act
        UserResponse result = userMapper.toResponse(user);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@email.com", result.getEmail());
        assertNotNull(result.getCreatedAt());
    }
}
