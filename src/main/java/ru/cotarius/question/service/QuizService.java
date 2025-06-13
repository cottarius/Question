package ru.cotarius.question.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cotarius.question.entity.QuizQuestion;
import ru.cotarius.question.repository.QuizQuestionRepository;

import java.util.*;

/**
 * Сервис для управления логикой викторины.
 * Использует {@link HttpSession} для хранения состояния между запросами пользователя.
 */
@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizQuestionRepository repository;
    private final HttpSession session;

    /**
     * Инициализирует сессию викторины:
     * - загружает случайные 10 вопросов;
     * - перемешивает их;
     * - сбрасывает счётчик и индекс текущего вопроса.
     */
    public void initQuizSession() {
        List<QuizQuestion> questionPool = new ArrayList<>(repository.findRandom10());
        Collections.shuffle(questionPool);

        session.setAttribute("questionPool", questionPool);
        session.setAttribute("currentIndex", 0);
        session.setAttribute("correctCount", 0);
    }

    /**
     * Проверяет правильность ответа на текущий вопрос и при необходимости увеличивает счётчик правильных ответов.
     *
     * @param id     идентификатор вопроса
     * @param answer выбранный пользователем ответ
     */
    public void checkAnswerAndUpdate(Long id, String answer) {
        QuizQuestion question = repository.findById(id).orElseThrow();
        int correctCount = (int) session.getAttribute("correctCount");

        if (question.getCorrectAnswer().equals(answer)) {
            session.setAttribute("correctCount", correctCount + 1);
        }
    }

    /**
     * Возвращает следующий вопрос из пула. Если вопросы закончились — возвращает редирект на финальную страницу.
     *
     * @return карта с текущим вопросом и списком вариантов ответов, либо редирект
     */
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

    /**
     * Формирует финальный результат викторины: количество правильных ответов и общее число вопросов.
     *
     * @return карта с результатом викторины
     */
    public Map<String, Object> getQuizResult() {
        int correctCount = (int) session.getAttribute("correctCount");
        List<QuizQuestion> questionPool = (List<QuizQuestion>) session.getAttribute("questionPool");

        Map<String, Object> result = new HashMap<>();
        result.put("score", correctCount);
        result.put("total", questionPool.size());
        return result;
    }
}