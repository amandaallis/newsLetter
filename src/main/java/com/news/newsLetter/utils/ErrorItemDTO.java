package com.news.newsLetter.utils;

import org.springframework.util.Assert;
import org.springframework.validation.FieldError;

public class ErrorItemDTO {

    private String field;
    private String message;

    public ErrorItemDTO(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

    public ErrorItemDTO(String field, String message) {
        Assert.notNull(field, "Field description must not be null");
        Assert.isTrue(!field.isEmpty(), "Field description must not be empty");

        Assert.notNull(message, "Message description must not be null");
        Assert.isTrue(!message.isEmpty(), "Message description must not be empty");

        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}