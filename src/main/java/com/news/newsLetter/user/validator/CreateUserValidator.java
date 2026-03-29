package com.news.newsLetter.user.validator;

import com.news.newsLetter.user.dto.CreateUserRequest;
import com.news.newsLetter.user.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateUserValidator implements Validator {

    private final UserService userService;

    public CreateUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserRequest request = (CreateUserRequest) target;

        if (request.getEmail() != null && userService.emailExists(request.getEmail())) {
            errors.rejectValue("email", "email.exists", "Email is already registered");
        }
    }
}
