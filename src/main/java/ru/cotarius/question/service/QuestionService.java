package ru.cotarius.question.service;

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
public class QuestionService {
    private final QuestionRepository questionRepository;
    private int index = 32;

    public Optional<Question> findById(long id) {
        return questionRepository.findById(id);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Question getPatthernsQuestion() {
        List<Question> questions = getQuestionsFromTheme(Theme.PATTERNS_ALGORITHMS);
        checkIndex(questions);
        return questions.get(index);
    }

    public Question getQuestionFromSqlQuestions() {
        List<Question> sqlQuestions = getQuestionsFromTheme(Theme.SQL_DATABASE);
        checkIndex(sqlQuestions);
        return sqlQuestions.get(index++);
    }

    public Question getQuestionFromPrimaryQuestions() {
        List<Question> primaryQuestions = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            if (question.isImpotent()) {
                primaryQuestions.add(question);
            }
        }
        checkIndex(primaryQuestions);
        return primaryQuestions.get(index++);
    }

    public Question getQuestionFromCore3Questions() {
        List<Question> core3Questions = getQuestionsFromTheme(Theme.CORE3_MULTITHREADING);
        checkIndex(core3Questions);
        return core3Questions.get(index++);
    }

    public Question getQuestionFromCore2Questions() {
        List<Question> core2Questions = getQuestionsFromTheme(Theme.CORE2_COLLECTIONS);
        checkIndex(core2Questions);
        return core2Questions.get(index++);
    }

    public Question getQuestionFromCore1Questions() {
        List<Question> core1Questions = getQuestionsFromTheme(Theme.CORE1);
        checkIndex(core1Questions);
        return core1Questions.get(index++);
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

    public void checkIndex(List<Question> questions) {
        if (index >= questions.size()) {
            index = 0;
        }
    }
}
