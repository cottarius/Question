package ru.cotarius.question.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cotarius.question.entity.QuizQuestion;
import ru.cotarius.question.repository.QuizQuestionRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository repository;
    private final HttpSession session;

    public void initQuizSession() {
        List<QuizQuestion> questionPool = new ArrayList<>(repository.findRandom10());
        Collections.shuffle(questionPool);

        session.setAttribute("questionPool", questionPool);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("correctCount", 0);
    }

    public void checkAnswerAndUpdate(Long id, String answer) {
        QuizQuestion question = repository.findById(id).orElseThrow();
        int correctCount = (int) session.getAttribute("correctCount");

        if (question.getCorrectAnswer().equals(answer)) {
            session.setAttribute("correctCount", correctCount + 1);
        }
    }

    public Map<String, Object> getNextQuestion() {
        List<QuizQuestion> questionPool = (List<QuizQuestion>) session.getAttribute("questionPool");
        int currentIndex = (int) session.getAttribute("currentIndex");

        if (currentIndex >= questionPool.size()) {
            return Collections.singletonMap("redirect", "/quiz/end");
        }

        QuizQuestion current = questionPool.get(currentIndex);
        session.setAttribute("currentIndex", currentIndex + 1);

        List<String> answers = new ArrayList<>(List.of(
                current.getCorrectAnswer(),
                current.getWrongAnswer(),
                current.getWrongAnswer2(),
                current.getWrongAnswer3()
        ));
        Collections.shuffle(answers);

        Map<String, Object> data = new HashMap<>();
        data.put("question", current);
        data.put("answers", answers);
        return data;
    }

    public Map<String, Object> getQuizResult() {
        int correctCount = (int) session.getAttribute("correctCount");
        List<QuizQuestion> questionPool = (List<QuizQuestion>) session.getAttribute("questionPool");

        Map<String, Object> result = new HashMap<>();
        result.put("score", correctCount);
        result.put("total", questionPool.size());
        return result;
    }
}