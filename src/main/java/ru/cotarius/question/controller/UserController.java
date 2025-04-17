package ru.cotarius.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.service.MyUserDetails;
import ru.cotarius.question.service.TelegramBotService;
import ru.cotarius.question.service.UserService;

/**
 * Контроллер для работы с пользователями.
 *
 * @author olegprokopenko
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TelegramBotService telegramBotService;

    @Value("${telegram.chat_id}")
    private String chatId;

    /**
     * Отображает форму для регистрации нового пользователя.
     *
     * @param model модель MVC для передачи объекта пользователя в представление.
     * @return имя шаблона страницы регистрации.
     */
    @GetMapping("/registration")
    public String newUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign-up";
    }

    /**
     * Обрабатывает регистрацию нового пользователя.
     * Проверяет совпадение пароля и нового ввода, а также ошибки валидации.
     * При успешной регистрации делегирует сохранение пользователя в сервис UserService.
     *
     * @param user объект пользователя для регистрации (валидация через @Valid).
     * @param confirmPassword повторный ввод пароля для проверки совпадения.
     * @param bindingResult результат валидации формы.
     * @param model модель MVC для передачи ошибок или аттрибутов в представление.
     * @return имя шаблона или результат метода регистрации из UserService.
     */
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               @RequestParam String confirmPassword,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "sign-up";
        }
        return userService.registerUser(user, model);
    }

    /**
     * Отображает форму профиля пользователя по его id.
     *
     * @param id идентификатор пользователя.
     * @param model модель MVC для передачи информации о пользователе.
     * @return имя шаблона страницы профиля.
     */
    @GetMapping("/user/{id}")
    public String getUserForm(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "index";
    }

    /**
     * Сохраняет пользователя, авторизованного через OAuth2 и отправляет уведомление в Telegram о входе.
     * Использует данные профиля (имя, email) из OAuth2.
     *
     * @param principal информация о пользователе из OAuth2.
     * @param model модель MVC для передачи данных пользователя в представление.
     * @return имя html-шаблона для отображения страницы пользователя.
     */
    @GetMapping("/oauth2LoginSuccess")
    public String oauth2LoginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // Используйте данные, полученные из Google, для отображения информации о пользователе
//        String googleId = principal.getAttribute("sub");
        String email = null;
        String name = null;
        if (principal.getAttributes().containsKey("default_email")) {
            email = principal.getAttribute("default_email");
            name = principal.getAttribute("real_name");
        } else if (principal.getAttributes().containsKey("email")) {
            email = principal.getAttribute("email");
            name = principal.getAttribute("name");
        }

        User user = userService.findByEmailIdOrCreateNew(email);

//        model.addAttribute("keyword", keyword);
        String message = name + ", " + email + " зашел на Java Quizzer через oauth2.0";
        telegramBotService.sendMessage(message, chatId);
        log.info(message);

        model.addAttribute("user", user);
        return "index"; // имя HTML-шаблона, который вы хотите отобразить
    }

    /**
     * Отправляет уведомление в Telegram о входе пользователя через стандартную форму логина.
     *
     * @param authentication информация об аутентифицированном пользователе.
     * @param model модель MVC для передачи данных пользователя в представление.
     * @return имя html-шаблона для страницы пользователя.
     */
    @GetMapping("/loginSuccess")
    public String loginSuccess(Authentication authentication, Model model) {
        Object principal = authentication.getPrincipal();

        MyUserDetails userDetails = (MyUserDetails) principal;
        User user = userDetails.getUser();
        String email = user.getEmail();
        String fullname = String.format("%s %s", user.getFirstname(), user.getLastname());
        String message = fullname + ", " + email + " зашел на Java Quizzer через login";
        telegramBotService.sendMessage(message, chatId);
        log.info(message);

        model.addAttribute("user", user);
        return "index";
    }
}
