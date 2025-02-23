package ru.cotarius.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.cotarius.question.service.UserService;

@Controller
@RequiredArgsConstructor
public class VerificationController {

    private final UserService userService;

    @GetMapping("/verify-email")
    public String showVerificationPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "verify-email";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String code, Model model) {
        if (userService.verifyEmail(email, code)) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Invalid verification code");
            model.addAttribute("email", email); // Возврат email для повторной попытки
            return "verify-email";
        }
    }
}
