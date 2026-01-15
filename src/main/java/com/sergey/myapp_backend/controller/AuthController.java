package com.sergey.myapp_backend.controller;

import com.sergey.myapp_backend.dto.LoginRequest;
import com.sergey.myapp_backend.dto.LoginResponse;
import com.sergey.myapp_backend.dto.RegisterRequest;
import com.sergey.myapp_backend.dto.AuthResponse;
import com.sergey.myapp_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);

        // Если произошла ошибка
        if (response.getMessage().contains("already exists")) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
    // ДОБАВЬТЕ ЭТОТ МЕТОД:
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);

        if (response.getToken().contains("Invalid")) {
            return ResponseEntity.status(401).body(response);
        }

        return ResponseEntity.ok(response);
    }

    // Простой тестовый эндпоинт для проверки
    @GetMapping("/test")
    public String test() {
        return "Auth controller is working!";
    }
}