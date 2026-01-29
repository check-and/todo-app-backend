package com.sergey.myapp_backend.service;

import com.sergey.myapp_backend.dto.LoginRequest;
import com.sergey.myapp_backend.dto.LoginResponse;
import com.sergey.myapp_backend.dto.RegisterRequest;
import com.sergey.myapp_backend.dto.AuthResponse;
import com.sergey.myapp_backend.model.User;
import com.sergey.myapp_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public AuthResponse register(RegisterRequest request) {
        // Проверяем, существует ли пользователь
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("Username already exists", null);
        }

        // Создаем пользователя с паролем в открытом виде (ТОЛЬКО ДЛЯ ТЕСТА!)
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Пароль без хеширования!
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return new AuthResponse("Registration successful", user.getId());
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            return new LoginResponse("Invalid credentials", null, null, null);
        }

        User user = userOptional.get();

        // Простое сравнение паролей (без хеширования)
        if (!request.getPassword().equals(user.getPassword())) {
            return new LoginResponse("Invalid credentials", null, null, null);
        }

        // Простой токен
        String token = "jwt_" + user.getId() + "_" + System.currentTimeMillis();

        return new LoginResponse(token, user.getId(), user.getEmail(), user.getUsername());
    }
}