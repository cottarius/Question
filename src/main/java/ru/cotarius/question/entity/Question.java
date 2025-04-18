package ru.cotarius.question.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, представляющий вопрос в системе.
 * Содержит текст вопроса, ответ, тему вопроса и флаг важности.
 *
 * @author olegprokopenko
 */
@Data
@Entity
@Table(name = "questions")
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    /**
     * Уникальный идентификатор вопроса.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Текст вопроса.
     */
    private String question;

    /**
     * Ответ на вопрос.
     */
    private String answer;

    /**
     * Тема вопроса.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private Theme theme;

    /**
     * Важность вопроса.
     */
    @Column(name = "is_impotent")
    private boolean isImpotent;
}
