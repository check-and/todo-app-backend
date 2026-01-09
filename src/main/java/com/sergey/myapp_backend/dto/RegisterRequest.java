package com.sergey.myapp_backend.dto;

public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String name;

    // Конструктор по умолчанию (обязателен для Spring)
    public RegisterRequest() {}

    // Геттеры и сеттеры
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}