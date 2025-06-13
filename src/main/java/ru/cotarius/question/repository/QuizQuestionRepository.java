package ru.cotarius.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cotarius.question.entity.QuizQuestion;

import java.util.List;

/**
 * Репозиторий для работы с сущностью QuizQuestion.
 */
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    /**
     * Метод, который возвращает рандомные 10 объектов QuizQuestion.
     *
     * @return список 10 рандомных QuizQuestion.
     */
    @Query(value = "SELECT * FROM quiz_questions ORDER BY random() LIMIT 10", nativeQuery = true)
    List<QuizQuestion> findRandom10();
}
