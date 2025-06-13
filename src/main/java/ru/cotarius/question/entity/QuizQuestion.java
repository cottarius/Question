package ru.cotarius.question.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Клас, представляющий викторину в системе.
 * Содержит вопрос, правильный ответ и три неправильных ответа.
 */
@Entity
@Table(name = "quiz_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestion {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Вопрос.
     */
    @Column(nullable = false)
    private String question;

    /**
     * Корректный ответ.
     */
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    /**
     * Некорректный ответ.
     */
    @Column(name = "wrong_answer", nullable = false)
    private String wrongAnswer;

    /**
     * Некорректный ответ 2.
     */
    @Column(name = "wrong_answer_2", nullable = false)
    private String wrongAnswer2;

    /**
     * Некорректный ответ 3.
     */
    @Column(name = "wrong_answer_3", nullable = false)
    private String wrongAnswer3;
}

