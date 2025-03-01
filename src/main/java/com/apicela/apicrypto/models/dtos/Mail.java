package com.apicela.apicrypto.models.dtos;

import java.time.LocalDateTime;

public record Mail(String to, String title, String message, LocalDateTime createdAt) {
    public Mail(String to, String title, String message) {
        this(to, title, message, LocalDateTime.now());
    }
}
