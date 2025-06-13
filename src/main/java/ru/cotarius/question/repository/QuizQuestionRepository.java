package ru.cotarius.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.cotarius.question.entity.QuizQuestion;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    @Query(value = "SELECT * FROM quiz_questions ORDER BY random() LIMIT 10", nativeQuery = true)
    List<QuizQuestion> findRandom10();
}
