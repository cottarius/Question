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

@Slf4j
@Controller
@RequiredArgsConstructor
public class VerificationController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/verify-email")
    public String showVerificationPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "verify-email";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String code, HttpServletRequest request, Model model) {
        if (userService.verifyEmail(email, code)) {
            return "redirect:/login";
        } else {
            // Проверяем, существует ли еще пользователь (возможно, он был удален)
            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty()) {
                // Пользователь удален после 3 попыток
                request.getSession().setAttribute("error", "Превышено количество попыток ввода кода. Пожалуйста, зарегистрируйтесь снова.");
                log.error("Превышено количество попыток ввода кода. Пожалуйста, зарегистрируйтесь снова.");
                return "redirect:/registration";

            } else {
                // Показываем ошибку и оставшиеся попытки
                int attemptsLeft = 3 - userOptional.get().getVerificationAttempts();
                model.addAttribute("error", "Неверный код. Осталось попыток: " + attemptsLeft);
                model.addAttribute("email", email);
                return "verify-email";
            }
        }
    }
}
