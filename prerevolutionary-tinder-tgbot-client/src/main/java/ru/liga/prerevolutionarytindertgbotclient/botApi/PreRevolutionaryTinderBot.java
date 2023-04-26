package ru.liga.prerevolutionarytindertgbotclient.botApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class PreRevolutionaryTinderBot extends TelegramLongPollingBot {
    @Value("${bot-name}")
    private String BOT_NAME;
    @Value("${bot-token}")
    private String BOT_TOKEN;

    private final TelegramFacade telegramFacade;

    public PreRevolutionaryTinderBot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            List<PartialBotApiMethod<?>> replyMessagesToUser = telegramFacade.handleUpdate(update);
            replyMessagesToUser.forEach(this::sendReply);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void sendReply(PartialBotApiMethod<?> reply) {
        try {
            if (reply instanceof SendPhoto) {
                execute((SendPhoto) reply);
            }
            if (reply instanceof SendMessage) {
                execute((SendMessage) reply);
            }
        } catch (RuntimeException | TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }


}
