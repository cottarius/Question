package ru.cotarius.question.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.cotarius.question.entity.Question;
import ru.cotarius.question.entity.Theme;
import ru.cotarius.question.service.QuestionService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void QuestionRepositoryFindAllShouldReturnAllQuestions() {
        Question question1 = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question question2 = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE1, false);
        Question question3 = new Question(3L, "Вопрос 3", "Ответ 3", Theme.SPRING, true);
//        Question question4 = new Question(1L, "Вопрос 4", "Ответ 4", Theme.SPRING, false);
//        Question question5 = new Question(1L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
//        Question question6 = new Question(1L, "Вопрос 6", "Ответ 6", Theme.HIBERNATE_JDBC, false);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        List<Question> questionList = questionRepository.findAll();

        assertThat(questionList.size()).isEqualTo(3);
        assertThat(questionList.get(0)).isEqualTo(question1);
        assertThat(questionList).isNotNull();
    }

    @Test
    public void QuestionRepositoryFindByIdShouldReturnQuestion() {
        Question question1 = new Question(1L, "Вопрос 1", "Ответ 1", Theme.CORE1, true);
        Question question2 = new Question(2L, "Вопрос 2", "Ответ 2", Theme.CORE1, false);
        Question question3 = new Question(3L, "Вопрос 3", "Ответ 3", Theme.SPRING, true);
//        Question question4 = new Question(1L, "Вопрос 4", "Ответ 4", Theme.SPRING, false);
//        Question question5 = new Question(1L, "Вопрос 5", "Ответ 5", Theme.HIBERNATE_JDBC, true);
//        Question question6 = new Question(1L, "Вопрос 6", "Ответ 6", Theme.HIBERNATE_JDBC, false);

        questionRepository.save(question1);
        questionRepository.save(question2);
        questionRepository.save(question3);

        Question question = questionRepository.findById(question1.getId()).get();

        assertThat(question.getId()).isEqualTo(question1.getId());
        assertThat(questionRepository).isNotNull();
    }
}
