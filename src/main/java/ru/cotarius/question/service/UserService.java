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

/**
 * Сервис управления пользователями, включая регистрацию, верификацию email и сохранение.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Регистрирует нового пользователя с отправкой кода подтверждения на email.
     * Если username или email уже существуют — возвращает ошибку в модель.
     *
     * @param user  пользовательские данные
     * @param model модель для отображения ошибок
     * @return путь к следующей странице
     */
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

        String verificationCode = emailService.sendVerificationCode(user.getEmail());
        user.setVerificationCode(verificationCode);
        user.setEmailVerified(false);

        saveUser(user);

        log.info("Пользователь временно сохранен для верификации: {}", user.getEmail());

        model.addAttribute("email", user.getEmail());
        return "redirect:/verify-email?email=" + user.getEmail();
    }

    /**
     * Сохраняет пользователя в базе данных с шифрованием пароля и нормализацией email/username.
     *
     * @param user пользователь для сохранения
     * @return сохранённый пользователь
     */
    public User saveUser(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        log.debug("Пользователь сохранен: {}", savedUser);
        return savedUser;
    }

    /**
     * Верифицирует пользователя по email и коду. После 3-х неудачных попыток — удаляет пользователя.
     *
     * @param email email пользователя
     * @param code  введённый код подтверждения
     * @return true — если верификация успешна, иначе false
     */
    public boolean verifyEmail(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setVerificationAttempts(user.getVerificationAttempts() + 1);

            if (user.getVerificationCode().equals(code)) {
                user.setEmailVerified(true);
                log.info("Email успешно верифицирован: {}", email);
                return true;
            } else {
                log.warn("Неудачная попытка верификации email: {}. Попытка №{}", email, user.getVerificationAttempts());

                if (user.getVerificationAttempts() >= 3) {
                    userRepository.delete(user);
                    log.warn("Пользователь {} удален из-за 3 неудачных попыток верификации", email);
                } else {
                    userRepository.save(user);
                }
            }
        }
        return false;
    }

    /**
     * Находит пользователя по его ID.
     *
     * @param id ID пользователя
     * @return пользователь или null, если не найден
     */
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Находит пользователя по email. Если не найден — создаёт нового с ролью USER.
     *
     * @param email email пользователя
     * @return найденный или созданный пользователь
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
