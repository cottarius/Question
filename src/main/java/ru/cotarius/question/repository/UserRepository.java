package ru.cotarius.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cotarius.question.entity.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User.
 *
 * @author olegprokopenko
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
