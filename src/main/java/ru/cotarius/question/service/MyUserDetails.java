package ru.cotarius.question.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.cotarius.question.entity.User;

import java.util.Arrays;
import java.util.Collection;

/**
 * Реализация {@link UserDetails} для использования с Spring Security.
 * Обёртка над сущностью {@link User}, предоставляющая информацию о пользователе
 * для механизма аутентификации и авторизации.
 *
 * Использует роль пользователя в виде строки, разделённой запятыми, для формирования списка прав.
 */
public record MyUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRole().toString().split(", "))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * Возвращает список прав (ролей), присвоенных пользователю.
     *
     * @return коллекция {@link GrantedAuthority}, извлечённая из роли пользователя
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Возвращает зашифрованный пароль пользователя.
     *
     * @return строка пароля
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
