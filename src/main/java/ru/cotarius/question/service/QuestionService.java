package ru.cotarius.question.service;

import org.springframework.stereotype.Service;
import ru.cotarius.question.entity.Question;

import java.util.List;
import java.util.Optional;

@Service
public interface QuestionService {

    Optional<Question> findById(long id);
    List<Question> findAll();
}
