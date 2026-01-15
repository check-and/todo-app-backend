package com.sergey.myapp_backend.controller;

import com.sergey.myapp_backend.model.User;
import com.sergey.myapp_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                                     //не эндпоинт, а "табличка на двери" класса
public class TestController {

    @Autowired                                      //не эндпоинт, а "принеси мне готовый объект"
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в мой бэкенд! Эндпоинты: /test, /api/tasks (позже)";
    }

    @GetMapping("/test")
    public String test() {
        return "Spring Boot работает! Проверка API";
    }

    // 👇 ДОБАВЬ ЭТИ ДВА МЕТОДА 👇

    @GetMapping("/db-check")
    public String dbCheck() {
        long count = userRepository.count();
        return "✅ База данных подключена! Пользователей: " + count;
    }

    @GetMapping("/create-user")
    public String createUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setEmail("test@example.com");


        userRepository.save(user);

        return "✅ Пользователь создан! ID: " + user.getId();
    }
    @GetMapping("/fix-db")
    public String fixDatabase() {
        try {
            // Найдем всех пользователей с пустым email
            List<User> users = userRepository.findAll();
            int fixed = 0;

            for (User user : users) {
                if (user.getEmail() != null && user.getEmail().isEmpty()) {
                    user.setEmail(null);
                    userRepository.save(user);
                    fixed++;
                }
            }

            return "✅ База исправлена! Исправлено записей: " + fixed;
        } catch (Exception e) {
            return "❌ Ошибка: " + e.getMessage();
        }
    }
}