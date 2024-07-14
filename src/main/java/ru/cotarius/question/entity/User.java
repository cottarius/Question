package ru.cotarius.question.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "email should not be empty")
    private String username;

    private String password;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    @Column(name = "email")
    private String email;

    @Column(name = "google_id")
    private String googleId;

    @Enumerated(EnumType.STRING)
    private Role role;
}
