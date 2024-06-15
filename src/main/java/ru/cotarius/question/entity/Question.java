package ru.cotarius.question.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "questions")
@AllArgsConstructor
@NoArgsConstructor
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
