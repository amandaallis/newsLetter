package com.news.newsLetter.infra;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptionService {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordEncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
