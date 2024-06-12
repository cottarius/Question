package ru.cotarius.question.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String question;

    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private Theme theme;

    @Column(name = "is_impotent")
    private boolean isImpotent;
}
