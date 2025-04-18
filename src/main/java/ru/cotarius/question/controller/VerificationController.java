package ru.cotarius.question.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.repository.UserRepository;
import ru.cotarius.question.service.UserService;

import java.util.Optional;

/**
 * Контроллер для обработки операций верификации email пользователя.
 * Обеспечивает отображение страницы верификации и обработку введенного кода подтверждения.
 *
 * @author olegprokopenko
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class VerificationController {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * Отображает страницу верификации email.
     *
     * @param email email пользователя, который требуется верифицировать
     * @param model модель для передачи данных в представление
     * @return имя представления страницы верификации ("verify-email")
     */
    @GetMapping("/verify-email")
    public String showVerificationPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "verify-email";
    }

    /**
     * Обрабатывает отправку кода верификации.
     * Проверяет соответствие введенного кода и осуществляет верификацию пользователя.
     * В случае неудачи показывает количество оставшихся попыток.
     * При превышении лимита попыток перенаправляет на страницу регистрации.
     *
     * @param email email пользователя для верификации
     * @param code введенный код подтверждения
     * @param request объект HttpServletRequest для работы с сессией
     * @param model модель для передачи данных в представление
     * @return перенаправление на главную страницу при успешной верификации,
     *         возврат на страницу верификации с сообщением об ошибке при неудаче,
     *         или перенаправление на страницу регистрации при превышении попыток
     */
    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String code,
                              HttpServletRequest request, Model model) {
        if (userService.verifyEmail(email, code)) {
            return "redirect:/";
        } else {
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                request.getSession().setAttribute("error",
                        "Превышено количество попыток ввода кода. Пожалуйста, зарегистрируйтесь снова.");
                log.error("Превышено количество попыток ввода кода. Пожалуйста, зарегистрируйтесь снова.");
                return "redirect:/registration";
            } else {
                int attemptsLeft = 3 - userOptional.get().getVerificationAttempts();
                model.addAttribute("error", "Неверный код. Осталось попыток: " + attemptsLeft);
                model.addAttribute("email", email);
                return "verify-email";
            }
        }
    }
}
