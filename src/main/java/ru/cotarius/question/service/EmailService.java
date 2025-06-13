package ru.cotarius.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Сервис для отправки электронных писем с кодом верификации.
 * Использует {@link JavaMailSender} для отправки простых текстовых сообщений.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Random random = new Random();

    /**
     * Генерирует случайный 6-значный код и отправляет его на указанный email.
     *
     * @param email адрес получателя
     * @return отправленный код верификации
     * @throws RuntimeException если отправка письма не удалась
     */
    public String sendVerificationCode(String email) {
        String code = generateVerificationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + code);
        try {
            mailSender.send(message);
            log.info("Verification code sent to email: {}", email); // Логирование успешной отправки
        } catch (Exception e) {
            log.error("Failed to send verification code to email: {}", email, e); // Логирование ошибки
            throw new RuntimeException("Failed to send email", e);
        }
        return code;
    }

    /**
     * Генерирует случайный 6-значный числовой код верификации.
     *
     * @return строка с кодом
     */
    private String generateVerificationCode() {
        return String.format("%06d", random.nextInt(1000000));
    }
}
