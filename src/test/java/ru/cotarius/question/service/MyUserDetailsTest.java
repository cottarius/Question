package ru.cotarius.question.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import ru.cotarius.question.entity.Role;
import ru.cotarius.question.entity.User;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тестирование обёртки MyUserDetails для Spring Security")
class MyUserDetailsTest {

    @Test
    @DisplayName("Получение имени пользователя")
    void testGetUsername() {
        User user = new User();
        user.setUsername("testuser");
        MyUserDetails userDetails = new MyUserDetails(user);

        assertEquals("testuser", userDetails.getUsername());
    }

    @Test
    @DisplayName("Получение зашифрованного пароля")
    void testGetPassword() {
        User user = new User();
        user.setPassword("encryptedPass");
        MyUserDetails userDetails = new MyUserDetails(user);

        assertEquals("encryptedPass", userDetails.getPassword());
    }

    @Test
    @DisplayName("Получение ролей пользователя в виде GrantedAuthority")
    void testGetAuthorities() {
        User user = new User();
        user.setRole(Role.USER); // допустим, Role.USER.toString() = "ROLE_USER"
        MyUserDetails userDetails = new MyUserDetails(user);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("USER")));
    }
}