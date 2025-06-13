package ru.cotarius.question.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.cotarius.question.entity.Role;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование сервиса пользователей (UserService)")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Регистрация пользователя - email уже существует")
    void testRegisterUser_EmailExists() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("user1");
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        Model model = new ConcurrentModel();
        String view = userService.registerUser(user, model);

        assertEquals("registration", view);
        assertEquals("Такой Email уже существует", model.getAttribute("error"));
    }

    @Test
    @DisplayName("Сохранение пользователя с хешированием пароля")
    void testSaveUser_PasswordEncodedAndSaved() {
        User user = new User();
        user.setUsername("UserName");
        user.setPassword("pass");
        user.setEmail("Email@EXAMPLE.com");

        when(passwordEncoder.encode("pass")).thenReturn("encoded_pass");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.saveUser(user);

        assertEquals("username", saved.getUsername());
        assertEquals("email@example.com", saved.getEmail());
        assertEquals("encoded_pass", saved.getPassword());
        assertEquals(Role.USER, saved.getRole());
    }

    @Test
    @DisplayName("Успешная верификация email")
    void testVerifyEmail_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setVerificationCode("123456");
        user.setVerificationAttempts(0);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.verifyEmail("test@example.com", "123456");

        assertTrue(result);
        assertTrue(user.isEmailVerified());
    }

    @Test
    @DisplayName("Ошибка верификации email - удаление после 3 попыток")
    void testVerifyEmail_FailureAndDeleteAfterThreeAttempts() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setVerificationCode("000000");
        user.setVerificationAttempts(2);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.verifyEmail("test@example.com", "123456");

        assertFalse(result);
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("Поиск пользователя по email - пользователь найден")
    void testFindByEmailIdOrCreateNew_UserExists() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmailIdOrCreateNew("existing@example.com");
        assertEquals(user, result);
    }

    @Test
    @DisplayName("Поиск пользователя по email - создание нового пользователя")
    void testFindByEmailIdOrCreateNew_CreatesNewUser() {
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.findByEmailIdOrCreateNew("new@example.com");

        assertEquals("new@example.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
        verify(userRepository).save(captor.capture());
    }
}