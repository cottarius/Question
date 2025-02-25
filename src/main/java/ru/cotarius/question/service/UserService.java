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
            log.warn("Такой Username уже существует: {}", user.getUsername());
            model.addAttribute("error", "Такой Username уже существует");
            return "registration";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Такой Email уже существует: {}", user.getEmail());
            model.addAttribute("error", "Такой Email уже существует");
            return "registration";
        }

        // Отправка проверочного кода
        String verificationCode = emailService.sendVerificationCode(user.getEmail());
        user.setVerificationCode(verificationCode);
        user.setEmailVerified(false);

        saveUser(user);

        log.info("Пользователь успешно зарегестрирован: {}", user.getEmail());

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
        log.debug("Пользователь сохранен: {}", savedUser);
        return savedUser;
    }

    public boolean verifyEmail(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getVerificationCode().equals(code)) {
                user.setEmailVerified(true);
                userRepository.save(user);
                log.info("Email успешно верифицирован: {}", email);
                return true;
            } else {
                log.warn("Введен неправильный верификационный код email: {}", email);
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
