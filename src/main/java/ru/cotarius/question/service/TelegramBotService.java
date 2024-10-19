package ru.cotarius.question.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {
    @Autowired
    public TelegramBotService(@Value("${telegram.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "Cotarius_bot";
    }

    public void sendMessage(String message, String chatID){
        SendMessage sendMessage = new SendMessage();
        if (chatID != null) {
            sendMessage.setChatId(chatID);
            sendMessage.setText(message);
            try {
                execute(sendMessage);
                log.atLevel(Level.INFO).log("Message sent to telegram");
            } catch (TelegramApiException e) {
                log.atLevel(Level.WARN).log("Error while sending message: " + e.getMessage());
            }
        }
    }
}
