package com.news.newsLetter.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public class ValidationUtils {

    public static ResponseEntity<Object> handleValidationErrors(BindingResult bindingResult) {

        List<ErrorItemDTO> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new ErrorItemDTO(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}