package com.news.newsLetter.user.controller;

import com.news.newsLetter.user.dto.CreateUserRequest;
import com.news.newsLetter.user.dto.UserResponse;
import com.news.newsLetter.user.service.UserService;
import com.news.newsLetter.user.validator.CreateUserValidator;
import com.news.newsLetter.utils.ValidationUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CreateUserValidator validator;

    public UserController(UserService userService, CreateUserValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidationUtils.handleValidationErrors(bindingResult);
        }

        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
