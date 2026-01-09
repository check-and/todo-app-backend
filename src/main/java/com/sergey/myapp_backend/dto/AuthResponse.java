package com.sergey.myapp_backend.dto;

public class AuthResponse {
    private String message;
    private Long userId;

    // Конструкторы
    public AuthResponse() {}

    public AuthResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    // Геттеры и сеттеры
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}