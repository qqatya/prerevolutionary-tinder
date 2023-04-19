package ru.liga.prerevolutionarytindertgbotclient.botApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
            BotApiMethod<?> replyMessageToUser = telegramFacade.handleUpdate(update);
            execute(replyMessageToUser);
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
