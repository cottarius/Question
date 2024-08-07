package ru.cotarius.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import ru.cotarius.question.entity.Role;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String registerUser(User user, Model model) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "registration";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists");
            return "registration";
        }

        saveUser(user);
        return "redirect:/login";
    }

    public User saveUser(User user) {
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        user.setRole(Role.USER);

        return userRepository.save(user);
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
