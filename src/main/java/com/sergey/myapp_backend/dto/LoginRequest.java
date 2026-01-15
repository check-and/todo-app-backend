package com.sergey.myapp_backend.dto;

public class LoginRequest {
    private String username; // ← ИЗМЕНИТЬ с "email" на "username"
    private String password;

    // Конструкторы
    public LoginRequest() {}
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}