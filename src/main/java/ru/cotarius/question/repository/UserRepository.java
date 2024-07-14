package ru.cotarius.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cotarius.question.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByGoogleId(String googleId);
    Optional<User> findByEmail(String email);
}
