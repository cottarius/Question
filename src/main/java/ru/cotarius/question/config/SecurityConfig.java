package ru.cotarius.question.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.cotarius.question.repository.UserRepository;
import ru.cotarius.question.service.MyUserDetailService;

/**
 * Конфигурационный класс Spring Security для настройки безопасности приложения.
 * Определяет правила аутентификации, авторизации, формы входа, выхода и OAuth2.
 *
 * @author olegprokopenko
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Создает и возвращает кодировщик паролей BCrypt.
     *
     * @return экземпляр BCryptPasswordEncoder для хеширования паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настраивает цепочку фильтров безопасности HTTP.
     *
     * @param http объект HttpSecurity для настройки.
     * @return сконфигурированная цепочка фильтров безопасности.
     * @throws Exception если произошла ошибка при настройке.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/registration**",
                                "/verify-email**",
                                "/css/**",
                                "/images/**",
                                "/fonts/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                                .loginPage("/login")
//                                .defaultSuccessUrl("/index")
                                .defaultSuccessUrl("/oauth2LoginSuccess", true) // для сохранения oauth2-пользователя в репозиторий
                                .failureUrl("/login")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/loginSuccess", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .build();
    }

    /**
     * Создает и настраивает провайдер аутентификации.
     *
     * @param userRepository репозиторий пользователей.
     * @return настроенный DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserRepository userRepository) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(userRepository));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Создает и возвращает сервис для работы с деталями пользователя.
     *
     * @param userRepository репозиторий пользователей.
     * @return реализацию UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new MyUserDetailService(userRepository);
    }
}
