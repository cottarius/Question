package ru.cotarius.question.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.service.UserService;

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
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        return userService.registerUser(user, model);
    }

    @GetMapping("/user/{id}")
    public String getUserForm(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "index";
    }

    /**
     * Метод для сохранения oauth2-пользователя в user repository
     * @param principal oauth2-пользователь
     * @param model
     * @return
     */
    @GetMapping("/oauth2LoginSuccess")
    public String oauth2LoginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        // Используйте данные, полученные из Google, для отображения информации о пользователе
//        String googleId = principal.getAttribute("sub");
        String email = principal.getAttribute("email");
//        String githubId = principal.getAttribute("id");

        User user = userService.findByEmailIdOrCreateNew(email);

//        model.addAttribute("keyword", keyword);

        model.addAttribute("user", user);
        return "index"; // имя HTML-шаблона, который вы хотите отобразить
    }
}
