package ru.cotarius.question.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;
import ru.cotarius.question.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockitoQuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;


    @Test
    public void getPrimaryQuestionsTest(){
        Question core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        Question core3Question = new Question(3L, "Вопрос 3", "Ответ 3", Theme.CORE3_MULTITHREADING, true);
        Question sqlQuestion = new Question(4L, "Вопрос 4", "Ответ 4", Theme.SQL_DATABASE, false);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question, sqlQuestion));

        List<Question> questions = questionService.getPrimaryQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(Question::isImpotent)).isTrue();
        assertThat(questions).hasSize(2);
        assertThat(questions.get(1)).isEqualTo(core3Question);
    }

    @Test
    public void getSpringQuestionsTest(){
        Question springQuestion = new Question(1L, "Вопрос 3", "Ответ 3", Theme.SPRING, true);
        Question sqlQuestion = new Question(2L, "Вопрос 4", "Ответ 4", Theme.SQL_DATABASE, false);
        Question hibernateQuestion = new Question(3L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(springQuestion, sqlQuestion, hibernateQuestion));

        List<Question> questions = questionService.getSpringQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.SPRING))).isTrue();
        assertThat(questions).hasSize(1);
    }

    @Test
    public void getHibernateQuestionsTest(){
        Question core3Question = new Question(3L, "Вопрос 3", "Ответ 3", Theme.CORE3_MULTITHREADING, true);
        Question sqlQuestion = new Question(4L, "Вопрос 4", "Ответ 4", Theme.SQL_DATABASE, false);
        Question hibernateQuestion = new Question(5L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core3Question, sqlQuestion, hibernateQuestion));

        List<Question> questions = questionService.getHibernateQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.HIBERNATE_JDBC))).isTrue();
        assertThat(questions).hasSize(1);
    }

    @Test
    public void getSqlQuestionsTest(){
        Question core3Question = new Question(3L, "Вопрос 3", "Ответ 3", Theme.CORE3_MULTITHREADING, true);
        Question sqlQuestion = new Question(4L, "Вопрос 4", "Ответ 4", Theme.SQL_DATABASE, false);
        Question hibernateQuestion = new Question(5L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core3Question, sqlQuestion, hibernateQuestion));

        List<Question> questions = questionService.getSqlQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.SQL_DATABASE))).isTrue();
        assertThat(questions).hasSize(1);
    }

    @Test
    public void getCore3QuestionsTest(){
        Question core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        Question core3Question = new Question(3L, "Вопрос 3", "Ответ 3", Theme.CORE3_MULTITHREADING, true);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question));

        List<Question> questions = questionService.getCore3Questions();

        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.CORE3_MULTITHREADING))).isTrue();

        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(core3Question);
        assertThat(questions).isNotNull();
    }

    @Test
    public void getCore2QuestionsTest(){
        Question core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question));

        List<Question> questions = questionService.getCore2Questions();

        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.CORE2_COLLECTIONS))).isTrue();

        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(core2Question);
        assertThat(questions).isNotNull();
    }

    @Test
    public void getCore1QuestionsTest(){
        Question core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question));

        List<Question> questions = questionService.getCore1Questions();
        for (Question question : questions) {
            assertThat(question.getTheme().equals(Theme.CORE1)).isTrue();
        }
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions).isNotNull();
        assertThat(questions.get(0)).isEqualTo(core1Question);
    }


    @Test
    public void testFindById() {
        Question core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        when(questionRepository.findById(core1Question.getId())).thenReturn(Optional.of(core1Question));
        Optional<Question> question = questionService.findById(1L);
        assertEquals("Вопрос 1", question.get().getQuestion());
    }

    @Test
    public void testFindAll() {
        Question core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question));

        List<Question> questions = questionService.findAll();
        assertEquals(2, questions.size());
        assertThat(questions).isNotNull();
    }
}
