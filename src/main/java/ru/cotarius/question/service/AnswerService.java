package ru.cotarius.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cotarius.question.entity.Answer;
import ru.cotarius.question.repository.AnswerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService{
    private final AnswerRepository answerRepository;

    public Optional<Answer> findByQuestionId(long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public List<Answer> findAll() {
        return answerRepository.findAll();
    }
}
