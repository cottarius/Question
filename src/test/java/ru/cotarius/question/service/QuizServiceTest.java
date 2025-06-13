package ru.cotarius.question.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cotarius.question.entity.QuizQuestion;
import ru.cotarius.question.repository.QuizQuestionRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование сервиса викторины (QuizService)")
class QuizServiceTest {

    @Mock
    private QuizQuestionRepository repository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private QuizService quizService;

    private List<QuizQuestion> questionList;

    @BeforeEach
    void setUp() {
        QuizQuestion question = new QuizQuestion();
        question.setId(1L);
        question.setCorrectAnswer("correct");
        question.setWrongAnswer("wrong1");
        question.setWrongAnswer2("wrong2");
        question.setWrongAnswer3("wrong3");

        questionList = Collections.singletonList(question);
    }

    @Test
    @DisplayName("Инициализация викторины должна сохранять данные в сессии")
    void testInitQuizSession() {
        when(repository.findRandom10()).thenReturn(questionList);

        quizService.initQuizSession();

        verify(session).setAttribute(eq("questionPool"), any());
        verify(session).setAttribute("currentIndex", 0);
        verify(session).setAttribute("correctCount", 0);
    }

    @Test
    @DisplayName("Правильный ответ должен увеличивать счётчик правильных ответов")
    void testCheckAnswerAndUpdate_CorrectAnswer() {
        QuizQuestion question = questionList.get(0);

        when(repository.findById(1L)).thenReturn(Optional.of(question));
        when(session.getAttribute("correctCount")).thenReturn(1);

        quizService.checkAnswerAndUpdate(1L, "correct");

        verify(session).setAttribute("correctCount", 2);
    }

    @Test
    @DisplayName("Неправильный ответ не должен изменять счётчик")
    void testCheckAnswerAndUpdate_WrongAnswer() {
        QuizQuestion question = questionList.get(0);

        when(repository.findById(1L)).thenReturn(Optional.of(question));
        when(session.getAttribute("correctCount")).thenReturn(1);

        quizService.checkAnswerAndUpdate(1L, "wrong");

        verify(session, never()).setAttribute("correctCount", 2);
    }

    @Test
    @DisplayName("Получение следующего вопроса должно возвращать вопрос и ответы")
    void testGetNextQuestion() {
        when(session.getAttribute("questionPool")).thenReturn(questionList);
        when(session.getAttribute("currentIndex")).thenReturn(0);

        Map<String, Object> result = quizService.getNextQuestion();

        assertTrue(result.containsKey("question"));
        assertTrue(result.containsKey("answers"));

        verify(session).setAttribute("currentIndex", 1);
    }

    @Test
    @DisplayName("Если вопросов больше нет — должен быть редирект")
    void testGetNextQuestion_Redirect() {
        when(session.getAttribute("questionPool")).thenReturn(questionList);
        when(session.getAttribute("currentIndex")).thenReturn(1);

        Map<String, Object> result = quizService.getNextQuestion();

        assertEquals("/quiz/end", result.get("redirect"));
    }

    @Test
    @DisplayName("Возврат результата должен содержать правильные значения")
    void testGetQuizResult() {
        when(session.getAttribute("correctCount")).thenReturn(3);
        when(session.getAttribute("questionPool")).thenReturn(questionList);

        Map<String, Object> result = quizService.getQuizResult();

        assertEquals(3, result.get("score"));
        assertEquals(1, result.get("total"));
    }
}