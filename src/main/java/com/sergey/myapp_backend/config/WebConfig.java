package com.sergey.myapp_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // первое после получения данных от андроида - WebConfig.java - проверяет, разрешено ли Android приложению обращаться к серверу
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")              // Все URL
                .allowedOriginPatterns("*")               // Разрешаем ВСЕМ (включая Android)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешаем методы
                .allowedHeaders("*")                      // Разрешаем все заголовки
                .allowCredentials(true)                   // Разрешаем авторизацию
                .maxAge(3600);

    }
}