package ru.cotarius.question.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
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

    private final ChatClient chatClient;


    @Autowired
    public TelegramBotService(@Value("${telegram.token}") String botToken, ChatClient.Builder builder) {
        super(botToken);
        this.chatClient = builder
                .defaultSystem(s -> s.text("Отвечай строго на русском языке. Будь вежливым и используй правильную грамматику."))
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory(), "default", 10))
                .build();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("Получено сообщение :{}", update.getMessage().getText());
            String userMessage = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            log.info("Получен chatId:{}", chatId);

            String reply = chatClient.prompt()
                    .system("Отвечай на русском языке")
                    .user(userMessage)
                    .call()
                    .content();

            sendMessage(chatId.toString(), reply);
        }
    }

    @Override
    public String getBotUsername() {
        return "Cotarius";
    }

    public void sendMessage(String chatID, String message) {
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
