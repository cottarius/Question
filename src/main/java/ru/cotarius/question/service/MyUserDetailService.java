package ru.cotarius.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.repository.UserRepository;

import java.util.Optional;

/**
 * Реализация {@link UserDetailsService} для интеграции с Spring Security.
 * Загружает пользователя по имени пользователя из {@link UserRepository}
 * и оборачивает его в {@link MyUserDetails} для использования системой аутентификации.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает пользователя по имени пользователя.
     *
     * @param username имя пользователя, введённое при входе
     * @return объект {@link UserDetails}, обёрнутый в {@link MyUserDetails}
     * @throws UsernameNotFoundException если пользователь не найден в базе
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username.toLowerCase());
        if (user.isEmpty()) {
            log.info("User not found");
        }
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
