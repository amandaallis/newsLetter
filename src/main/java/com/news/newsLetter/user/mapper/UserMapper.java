package com.news.newsLetter.user.mapper;

import com.news.newsLetter.user.User;
import com.news.newsLetter.user.dto.CreateUserRequest;
import com.news.newsLetter.user.dto.UserResponse;
import com.news.newsLetter.infra.PasswordEncryptionService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncryptionService passwordEncryptionService;

    public UserMapper(PasswordEncryptionService passwordEncryptionService) {
        this.passwordEncryptionService = passwordEncryptionService;
    }

    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncryptionService.encrypt(request.getPassword()));
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
}
