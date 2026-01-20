package com.sergey.myapp_backend.service;

import com.sergey.myapp_backend.dto.LoginRequest;
import com.sergey.myapp_backend.dto.LoginResponse;
import com.sergey.myapp_backend.dto.RegisterRequest;
import com.sergey.myapp_backend.dto.AuthResponse;
import com.sergey.myapp_backend.model.User;
import com.sergey.myapp_backend.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
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

        // Проверяем email, если он есть
        if (request.getEmail() != null &&
                !request.getEmail().isEmpty() &&
                userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse("Email already exists", null);
        }

        // Хешируем пароль с BCrypt
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // Создаем пользователя
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashedPassword);
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

        // Проверяем пароль
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            return new LoginResponse("Invalid credentials", null, null, null);
        }

        // Генерируем простой токен
        String token = "jwt_" + System.currentTimeMillis() + "_" + user.getId();

        return new LoginResponse(token, user.getId(), user.getEmail(), user.getUsername());
    }
}