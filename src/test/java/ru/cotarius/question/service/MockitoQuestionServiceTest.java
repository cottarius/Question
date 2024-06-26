package ru.cotarius.question.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;
import ru.cotarius.question.repository.QuestionRepository;

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

    private Question core1Question;
    private Question core2Question;
    private Question core3Question;
    private Question sqlQuestion;
    private Question hibernateQuestion;
    private Question springQuestion;
    private Question patternQuestion;
    private Question technologiesQuestion;

    @BeforeEach
    public void setUp() {
        core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        core3Question = new Question(3L, "Вопрос 3", "Ответ 3", Theme.CORE3_MULTITHREADING, true);
        sqlQuestion = new Question(4L, "Вопрос 4", "Ответ 4", Theme.SQL_DATABASE, false);
        hibernateQuestion = new Question(5L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
        springQuestion = new Question(6L, "Вопрос 6", "Ответ 6", Theme.SPRING, false);
        patternQuestion = new Question(7L, "Вопрос 7", "Ответ 7", Theme.PATTERNS_ALGORITHMS, true);
        technologiesQuestion = new Question(8L, "Вопрос 8", "Ответ 8", Theme.FASHION_TECHNOLOGIES, false);
    }

    @Test
    public void getPrimaryQuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getPrimaryQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(Question::isImpotent)).isTrue();
        assertThat(questions).hasSize(4);
        assertThat(questions.get(1)).isEqualTo(core3Question);
    }

    @Test
    public void getSpringQuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getSpringQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.SPRING))).isTrue();
        assertThat(questions).hasSize(1);
    }

    @Test
    public void getHibernateQuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getHibernateQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.HIBERNATE_JDBC))).isTrue();
        assertThat(questions).hasSize(1);
    }

    @Test
    public void getSqlQuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getSqlQuestions();
        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.SQL_DATABASE))).isTrue();
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(sqlQuestion);
    }

    @Test
    public void getCore3QuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getCore3Questions();

        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.CORE3_MULTITHREADING))).isTrue();
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(core3Question);
    }

    @Test
    public void getCore2QuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getCore2Questions();

        assertThat(questions).isNotNull();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.CORE2_COLLECTIONS))).isTrue();
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(core2Question);
    }

    @Test
    public void getCore1QuestionsTest(){
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.getCore1Questions();
        assertThat(questions.stream().allMatch(q -> q.getTheme().equals(Theme.CORE1))).isTrue();
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
        assertEquals(core1Question, question.get());
    }

    @Test
    public void testFindAll() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(core1Question, core2Question, core3Question,
                sqlQuestion, hibernateQuestion, springQuestion, patternQuestion, technologiesQuestion));

        List<Question> questions = questionService.findAll();
        assertEquals(8, questions.size());
        assertThat(questions).isNotNull();
    }
}
