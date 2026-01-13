package com.sergey.myapp_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)                                 // Автоматически увеличивается (1, 2, 3...)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)                                                           // ОБЯЗАТЕЛЬНОЕ поле (не может быть пустым)
    private String password;

    @Column(nullable = true)                                                              // УНИКАЛЬНОЕ значение (нельзя два одинаковых)
    private String email;



    // Можно добавить роль (позже)
    // private String role = "USER";
}