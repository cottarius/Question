package ru.cotarius.question.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;
import ru.cotarius.question.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class QuestionService {
    private final QuestionRepository questionRepository;
    private int index = 0;

    public Optional<Question> findById(long id) {
        return questionRepository.findById(id);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getPrimaryQuestions() {
        List<Question> primaryQuestions = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            if (question.isImpotent()) {
                primaryQuestions.add(question);
            }
        }
        return primaryQuestions;
    }

    public List<Question> getQuestionsFromTheme(Theme theme) {
        List<Question> questions = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            if (question.getTheme().equals(theme)) {
                questions.add(question);
            }
        }
        return questions;
    }

    public List<Question> getHibernateQuestions() {
        return getQuestionsFromTheme(Theme.HIBERNATE_JDBC);
    }

    public List<Question> getCore1Questions() {
        return getQuestionsFromTheme(Theme.CORE1);
    }

    public List<Question> getCore2Questions() {
        return getQuestionsFromTheme(Theme.CORE2_COLLECTIONS);
    }

    public List<Question> getCore3Questions() {
        return getQuestionsFromTheme(Theme.CORE3_MULTITHREADING);
    }

    public List<Question> getFashionQuestions() {
        return getQuestionsFromTheme(Theme.FASHION_TECHNOLOGIES);
    }

    public List<Question> getPatternsQuestions() {
        return getQuestionsFromTheme(Theme.PATTERNS_ALGORITHMS);
    }

    public List<Question> getSqlQuestions() {
        return getQuestionsFromTheme(Theme.SQL_DATABASE);
    }

    public List<Question> getSpringQuestions() { return getQuestionsFromTheme(Theme.SPRING); }
}
