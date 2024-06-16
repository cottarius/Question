package ru.cotarius.question.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    Question core1Question;
    Question core2Question;
    Question core3Question;
    Question dataBaseQuestion;
    Question hibernateQuestion;
    Question springQuestion;
    Question patternQuestion;
    Question technologiesQuestion;

    @BeforeEach
    public void setUp() {
        core1Question = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        core2Question = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE2_COLLECTIONS, false);
        core3Question = new Question(3L, "Вопрос 3", "Ответ 3", Theme.CORE3_MULTITHREADING, true);
        dataBaseQuestion = new Question(4L, "Вопрос 4", "Ответ 4", Theme.SQL_DATABASE, false);
        hibernateQuestion = new Question(5L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
        springQuestion = new Question(6L, "Вопрос 6", "Ответ 6", Theme.SPRING, false);
        patternQuestion = new Question(7L, "Вопрос 7", "Ответ 7", Theme.PATTERNS_ALGORITHMS, true);
        technologiesQuestion = new Question(8L, "Вопрос 8", "Ответ 8", Theme.FASHION_TECHNOLOGIES, false);

        questionService.save(core1Question);
        questionService.save(core2Question);
        questionService.save(core3Question);
        questionService.save(dataBaseQuestion);
        questionService.save(hibernateQuestion);
        questionService.save(springQuestion);
        questionService.save(patternQuestion);
        questionService.save(technologiesQuestion);
    }

    @Test
    public void getSqlQuestionsShouldReturnSqlQuestions() {
        List<Question> sqlQuestions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.getTheme() == Theme.SQL_DATABASE) {
                sqlQuestions.add(question);
            }
        }
        for (Question question : sqlQuestions) {
            assertThat(question.getTheme().equals(Theme.SQL_DATABASE)).isTrue();
        }
        assertThat(sqlQuestions.size()).isEqualTo(1);
        assertThat(sqlQuestions).isNotNull();
        assertThat(sqlQuestions.get(0)).isEqualTo(dataBaseQuestion);
    }

    @Test
    public void getCore3QuestionsShouldReturnCore3Questions() {
        List<Question> core3Questions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.getTheme().equals(Theme.CORE3_MULTITHREADING)) {
                core3Questions.add(question);
            }
        }

        for (Question question : core3Questions) {
            assertThat(question.getTheme().equals(Theme.CORE3_MULTITHREADING)).isTrue();
        }
        assertThat(core3Questions.size()).isEqualTo(1);
        assertThat(core3Questions).isNotNull();
        assertThat(core3Questions.get(0)).isEqualTo(core3Question);
    }
    
    @Test
    public void getCore2QuestionsShouldReturnCore2Questions() {
        List<Question> core2Questions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.getTheme().equals(Theme.CORE2_COLLECTIONS)){
                core2Questions.add(question);
            }
        }

        for (Question question : core2Questions) {
            assertThat(question.getTheme().equals(Theme.CORE2_COLLECTIONS)).isTrue();
        }
        assertThat(core2Questions.size()).isEqualTo(1);
        assertThat(core2Questions).isNotNull();
        assertThat(core2Questions.get(0)).isEqualTo(core2Question);
    }

    @Test
    public void getCore1QuestionsShouldReturnCore1Questions() {
        List<Question> core1Questions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.getTheme().equals(Theme.CORE1)){
                core1Questions.add(question);
            }
        }

        for (Question question : core1Questions) {
            assertThat(question.getTheme().equals(Theme.CORE1)).isTrue();
        }
        assertThat(core1Questions.size()).isEqualTo(1);
        assertThat(core1Questions).isNotNull();
        assertThat(core1Questions.get(0)).isEqualTo(core1Question);
    }

    @Test
    public void getPrimaryQuestionsShouldReturnPrimaryQuestions() throws Exception {
        List<Question> primaryQuestions = new ArrayList<>();
        for (Question question : questionService.findAll()) {
            if (question.isImpotent()){
                primaryQuestions.add(question);
            }
        }

        for (Question question : primaryQuestions) {
            assertThat(question.isImpotent()).isTrue();
        }
        assertThat(primaryQuestions.size()).isEqualTo(4);
        assertThat(primaryQuestions).isNotNull();
        assertThat(primaryQuestions.get(0)).isEqualTo(core1Question);
    }

    @Test
    public void QuestionRepositoryFindAllShouldReturnAllQuestions() {
        List<Question> questionList = questionService.findAll();

        assertThat(questionList.size()).isEqualTo(8);
        assertThat(questionList.get(0)).isEqualTo(core1Question);
        assertThat(questionList).isNotNull();
    }

    @Test
    public void QuestionRepositoryFindByIdShouldReturnQuestion() {
        Question question = questionService.findById(core1Question.getId()).get();

        assertThat(question.getId()).isEqualTo(core1Question.getId());
        assertThat(questionService).isNotNull();
    }
}
