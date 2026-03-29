package com.news.newsLetter.user.service;

import com.news.newsLetter.user.User;
import com.news.newsLetter.user.dto.CreateUserRequest;
import com.news.newsLetter.user.dto.UserResponse;
import com.news.newsLetter.user.mapper.UserMapper;
import com.news.newsLetter.user.repository.UserRepository;
import com.news.newsLetter.infra.PasswordEncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncryptionService passwordEncryptionService;

    @InjectMocks
    private UserService userService;

    private CreateUserRequest createUserRequest;
    private User user;
    private UserResponse userResponse;
    private String encryptedPassword;

    @BeforeEach
    void setUp() {
        createUserRequest = new CreateUserRequest("John Silva", "john@email.com", "password123");
        encryptedPassword = "$2a$10$encryptedPassword123";
        
        user = new User();
        user.setId(1L);
        user.setName("John Silva");
        user.setEmail("john@email.com");
        user.setPassword(encryptedPassword);
        user.setCreatedAt(LocalDateTime.now());

        userResponse = new UserResponse(1L, "John Silva", "john@email.com", LocalDateTime.now());
    }

    @Test
    void createUser__should_save_user_successfully() {
        // Arrange
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);
        when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        UserResponse result = userService.createUser(createUserRequest);

        // Assert
        assertNotNull(result);
        assertEquals(userResponse.getId(), result.getId());
        assertEquals(userResponse.getName(), result.getName());
        assertEquals(userResponse.getEmail(), result.getEmail());

        verify(userMapper, times(1)).toEntity(createUserRequest);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponse(user);
    }

    @Test
    void createUser__should_map_request_to_entity_correctly() {
        // Arrange
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);
        when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        userService.createUser(createUserRequest);

        // Assert
        verify(userMapper).toEntity(createUserRequest);
    }

    @Test
    void createUser__should_return_user_response_correctly() {
        // Arrange
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);
        when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        UserResponse result = userService.createUser(createUserRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Silva", result.getName());
        assertEquals("john@email.com", result.getEmail());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void createUser__should_save_user_in_database() {
        // Arrange
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);
        when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        userService.createUser(createUserRequest);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser__should_encrypt_password_before_saving() {
        //
        when(passwordEncryptionService.encrypt("password123")).thenReturn(encryptedPassword);
        when(userMapper.toEntity(createUserRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // Act
        userService.createUser(createUserRequest);

        // Assert
        verify(userMapper, times(1)).toEntity(createUserRequest);
    }

    @Test
    void emailExists__should_return_true_when_email_exists() {
        // Arrange
        String email = "john@email.com";
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user));

        // Act
        boolean result = userService.emailExists(email);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void emailExists__should_return_false_when_email_does_not_exist() {
        // Arrange
        String email = "notexists@email.com";
        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.empty());

        // Act
        boolean result = userService.emailExists(email);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findByEmail(email);
    }
}
