package ru.cotarius.question.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.cotarius.question.entity.Role;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String newUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        user.setUsername(user.getUsername().toLowerCase());
        user.setRole(Role.USER);
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String getUserForm(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/oauth2LoginSuccess")
    public String oauth2LoginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // Используйте данные, полученные из Google, для отображения информации о пользователе
        String googleId = principal.getAttribute("sub");
        String email = principal.getAttribute("email");

        User user = userService.findByGoogleIdOrCreateNew(googleId, email);


//        model.addAttribute("keyword", keyword);

        model.addAttribute("user", user);
        return "index"; // имя HTML-шаблона, который вы хотите отобразить
    }
}
