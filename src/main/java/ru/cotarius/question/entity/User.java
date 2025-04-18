package ru.cotarius.question.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Класс, представляющий пользователя в системе.
 *
 * @author olegprokopenko
 */
@Entity
@Data
@Table(name = "users")
public class User {

    /**
     * Id пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     */
    private String firstname;

    /**
     * Фамилия пользователя.
     */
    private String lastname;

    /**
     * Логин пользователя.
     */
    @NotEmpty(message = "username should not be empty")
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Email пользователя.
     */
    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    @Column(name = "email")
    private String email;

    /**
     * Роль пользователя.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Код для подтверждения email.
     */
    private String verificationCode;

    /**
     * Статус подтверждения email.
     */
    private boolean emailVerified;

    /**
     * Количество попыток верификации.
     */
    private int verificationAttempts = 0;
}
