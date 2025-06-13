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

    /**
     * Находит пользователя по логину.
     *
     * @param username логин пользователя.
     * @return пользователь.
     */
    Optional<User> findByUsername(String username);

    /**
     * Находит пользователя по Email.
     *
     * @param email email пользователя.
     * @return пользователь.
     */
    Optional<User> findByEmail(String email);
}
