package ru.cotarius.question.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Тестирование сервиса отправки Email (EmailService)")
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("Успешная отправка кода верификации")
    void sendVerificationCode_shouldSendEmailAndReturnCode() {
        String testEmail = "test@example.com";

        String code = emailService.sendVerificationCode(testEmail);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage message = messageCaptor.getValue();
        assertEquals(testEmail, message.getTo()[0]);
        assertEquals("Verification Code", message.getSubject());
        assertTrue(message.getText().contains(code));
        assertTrue(Pattern.matches("\\d{6}", code));
    }

    @Test
    @DisplayName("Ошибка при отправке письма должна выбрасывать исключение")
    void sendVerificationCode_shouldThrowExceptionIfMailFails() {
        String testEmail = "fail@example.com";
        doThrow(new RuntimeException("Mail error")).when(mailSender).send(any(SimpleMailMessage.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> emailService.sendVerificationCode(testEmail));
        assertEquals("Failed to send email", exception.getMessage());
    }
}
