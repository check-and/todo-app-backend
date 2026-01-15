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

    @Autowired
    private JwtService jwtService; // Добавьте эту строку

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


    public LoginResponse login(LoginRequest request) {
        // Ищем по USERNAME (а не по email)
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            return new LoginResponse("Invalid username or password", null, null);
        }

        User user = userOptional.get();

        // Проверяем пароль
        if (!user.getPassword().equals(request.getPassword())) {
            return new LoginResponse("Invalid username or password", null, null);
        }

        // Генерируем JWT токен на основе username
        String token = jwtService.generateToken(user.getUsername());

        return new LoginResponse(token, user.getId(), user.getEmail());
    }
}
