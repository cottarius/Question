package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.cotarius.question.service.UserService;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "logout";
    }
}
