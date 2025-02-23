package ru.cotarius.question.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Random random = new Random();

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

    private String generateVerificationCode() {
        return String.format("%06d", random.nextInt(1000000));
    }
}
