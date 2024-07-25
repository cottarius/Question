package ru.cotarius.question.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.cotarius.question.entity.Role;
import ru.cotarius.question.entity.User;
import ru.cotarius.question.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MockitoUserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    private User user1;

    @BeforeEach
    public void setUp() {
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUser1");
        user1.setPassword("test1");
        user1.setEmail("testUser1@gmail.com");
        user1.setRole(Role.USER);
    }

    @Test
    public void getUserByIdTest() {
        // Настраиваем поведение мока
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        // Вызываем тестируемый метод
        User foundUser = userService.findById(1L);

        // Проверяем результаты
        assertNotNull(foundUser);
        assertEquals("testUser1", foundUser.getUsername());

        // Проверяем, что метод findById был вызван один раз
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void createUserTest() {
        // Настраиваем первый мок, чтобы он возвращал зашифрованный пароль
        when(passwordEncoder.encode(user1.getPassword())).thenReturn("encodedPassword");

        // Настраиваем второй мок, чтобы он сохранял и возвращал тестового пользователя
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User createdUser = userService.saveUser(user1);
        assertNotNull(createdUser);
        assertEquals("testuser1", createdUser.getUsername());
        assertEquals("encodedPassword", createdUser.getPassword());

        // Проверяем, что методы encode и save были вызваны один раз
        verify(passwordEncoder, times(1)).encode("test1");
        verify(userRepository, times(1)).save(user1);
    }
}
