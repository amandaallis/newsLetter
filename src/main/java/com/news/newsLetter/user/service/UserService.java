package com.news.newsLetter.user.service;

import com.news.newsLetter.user.User;
import com.news.newsLetter.user.dto.CreateUserRequest;
import com.news.newsLetter.user.dto.UserResponse;
import com.news.newsLetter.user.error.EmailAlreadyRegisteredException;
import com.news.newsLetter.user.mapper.UserMapper;
import com.news.newsLetter.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (emailExists(request.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
