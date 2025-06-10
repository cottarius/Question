package ru.cotarius.question.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quiz_questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(name = "wrong_answer", nullable = false)
    private String wrongAnswer;

    @Column(name = "wrong_answer_2", nullable = false)
    private String wrongAnswer2;

    @Column(name = "wrong_answer_3", nullable = false)
    private String wrongAnswer3;
}

