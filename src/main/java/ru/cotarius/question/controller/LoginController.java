package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов, связанных с аутентификацией и главной страницей.
 * Обеспечивает отображение страниц входа в систему и домашней страницы.
 *
 * @author olegprokopenko
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    /**
     * Обрабатывает GET-запрос для отображения страницы входа в систему.
     *
     * @return имя представления (view) страницы входа ("sign-in").
     */
    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }

    /**
     * Обрабатывает GET-запрос для отображения домашней страницы.
     *
     * @return имя представления (view) страницы домашней страницы ("index").
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
