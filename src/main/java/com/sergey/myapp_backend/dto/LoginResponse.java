package com.sergey.myapp_backend.dto;

public class LoginResponse {
    private String token;
    private Long userId;
    private String email;
    private String username; // Добавляем поле

    // Конструкторы
    public LoginResponse() {}

    public LoginResponse(String token, Long userId, String email, String username) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    // Геттеры и сеттеры
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}