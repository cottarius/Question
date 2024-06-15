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
public class QuestionService{
    private final QuestionRepository questionRepository;
//    private int index = 0;

    public Optional<Question> findById(long id) {
        return questionRepository.findById(id);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Question getQuestionFromPrimaryQuestions(int index) {
        List<Question> primaryQuestions = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            if (question.isImpotent()) {
                primaryQuestions.add(question);
            }
        }
        if (index >= primaryQuestions.size()) {
            index = 0;
        }
        return primaryQuestions.get(index);
    }

    public Question getQuestionFromCore3Questions(int index) {
        List<Question> core3Questions = getQuestionsFromTheme(Theme.CORE3_MULTITHREADING, index);
        return core3Questions.get(index);
    }

    public Question getQuestionFromCore2Questions(int index) {
        List<Question> core2Questions = getQuestionsFromTheme(Theme.CORE2_COLLECTIONS, index);
        return core2Questions.get(index);
    }

    public Question getQuestionFromCore1Questions(int index) {
        List<Question> core1Questions = getQuestionsFromTheme(Theme.CORE1, index);
        return core1Questions.get(index);
    }

    public List<Question> getQuestionsFromTheme(Theme theme, int index) {
        List<Question> questions = new ArrayList<>();
        for (Question question : questionRepository.findAll()) {
            if (question.getTheme().equals(theme)) {
                questions.add(question);
            }
        }
        if (index >= questions.size()) {
            index = 0;
        }
        return questions;
    }
}
