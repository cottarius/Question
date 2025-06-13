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

/**
 * Сервис Telegram-бота, использующий Spring AI ChatClient для генерации ответов.
 * Бот принимает текстовые сообщения и отвечает на них вежливо на русском языке.
 */
@Component
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    private final ChatClient chatClient;

    /**
     * Конструктор TelegramBotService.
     * Инициализирует {@link ChatClient} с русским системным промптом и in-memory историей.
     *
     * @param botToken токен Telegram-бота из application.properties
     * @param builder  билдер для конфигурации AI-клиента
     */
    @Autowired
    public TelegramBotService(@Value("${telegram.token}") String botToken, ChatClient.Builder builder) {
        super(botToken);
        this.chatClient = builder
                .defaultSystem(s -> s.text("Отвечай строго на русском языке. Будь вежливым и используй правильную грамматику."))
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory(), "default", 10))
                .build();
    }

    /**
     * Обрабатывает входящее обновление от Telegram.
     * Если получено текстовое сообщение — генерирует ответ через ChatClient и отправляет его обратно.
     *
     * @param update входящее обновление от Telegram
     */
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

            sendMessage(reply, chatId.toString());
        }
    }

    /**
     * Возвращает имя Telegram-бота.
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "Cotarius";
    }

    /**
     * Отправляет текстовое сообщение пользователю в Telegram.
     *
     * @param message текст для отправки
     * @param chatID  идентификатор чата получателя
     */
    public void sendMessage(String message, String chatID) {
        SendMessage sendMessage = new SendMessage();
        if (chatID != null) {
            sendMessage.setChatId(chatID);
            sendMessage.setText(message);
            try {
                execute(sendMessage);
                log.atLevel(Level.INFO).log("Message sent to user with chatId: {}", chatID);
            } catch (TelegramApiException e) {
                log.atLevel(Level.WARN).log("Error while sending message: " + e.getMessage());
            }
        }
    }
}
