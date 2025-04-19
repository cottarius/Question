package ru.cotarius.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cotarius.question.entity.Question;

/**
 * Репозиторий для работы с сущностью Question.
 *
 * @author olegprokopenko
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
