package ru.cotarius.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import ru.cotarius.question.entity.Role;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public String registerUser(User user, Model model) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.warn("Username already exists: {}", user.getUsername());
            model.addAttribute("error", "Username already exists");
            return "registration";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Email already exists: {}", user.getEmail());
            model.addAttribute("error", "Email already exists");
            return "registration";
        }

        // Отправка проверочного кода
        String verificationCode = emailService.sendVerificationCode(user.getEmail());
        user.setVerificationCode(verificationCode);
        user.setEmailVerified(false);

        saveUser(user);

        log.info("User registered successfully: {}", user.getEmail());

        // Перенаправление на страницу подтверждения email
        model.addAttribute("email", user.getEmail());
        return "verify-email";
    }

    public User saveUser(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        log.debug("User saved: {}", savedUser);
        return savedUser;
    }

    public boolean verifyEmail(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getVerificationCode().equals(code)) {
                user.setEmailVerified(true);
                userRepository.save(user);
                log.info("Email verified successfully: {}", email);
                return true;
            } else {
                log.warn("Invalid verification code for email: {}", email);
            }
        }
        return false;
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String login) {
        return userRepository.findByUsername(login).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Нет такого пользователя с login " + login));
    }

    /**
     * Поиск пользователя по google id в репозитории. Если пользователь существует - то он возвращается. Иначе - создается новый
     * @param email email пользователя
     * @return пользователь
     */
    public User findByEmailIdOrCreateNew(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            User newUser = new User();
            newUser.setEmail(email.toLowerCase());
            newUser.setRole(Role.USER);
            newUser.setUsername(email.toLowerCase());

            userRepository.save(newUser);
            return newUser;
        }
    }
}
