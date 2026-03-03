package com.sergey.myapp_backend.repository;

import com.sergey.myapp_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository                         // 2 ← Говорит Spring: "Этот интерфейс - репозиторий для работы с БД"
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);              // Проверить существование пользователя
}

//Spring Data JPA автоматически создает реализацию этого интерфейса
// во время запуска приложения. Вы просто говорите
// "мне нужен метод findByUsername", а Spring сам пишет код для обращения к БД.