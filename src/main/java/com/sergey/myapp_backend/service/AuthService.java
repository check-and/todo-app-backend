package com.sergey.myapp_backend.service;

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
        // Проверяем, не существует ли уже пользователь с таким username
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("Username already exists", null);
        }

        // 2. Обрабатываем email
        String email = processEmail(request.getEmail());

        // 3. Проверяем email только если он НЕ null и не пустой
        if (email != null && !email.isEmpty()) {
            // Проверяем уникальность вручную
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                return new AuthResponse("Email already exists", null);
            }
        }

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Пока без шифрования
        user.setEmail(request.getEmail());
        user.setName(request.getName());

        // Сохраняем в БД
        User savedUser = userRepository.save(user);

        // Возвращаем ответ
        return new AuthResponse("Registration successful", savedUser.getId());

    }
    private String processEmail(String rawEmail) {
        if (rawEmail == null) {
            return null;
        }

        String email = rawEmail.trim();

        // Если после trim пусто - возвращаем null
        if (email.isEmpty()) {
            return null;
        }

        // Дополнительная валидация email (опционально)
        if (!isValidEmailFormat(email)) {
            // Можно вернуть null или бросить исключение
            return email; // или null, если хочешь отклонять невалидные email
        }

        return email;
    }

    private boolean isValidEmailFormat(String email) {
        // Простая проверка формата email
        return email.contains("@") && email.contains(".");
    }
}
