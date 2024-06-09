package ru.cotarius.question.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String answer;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
