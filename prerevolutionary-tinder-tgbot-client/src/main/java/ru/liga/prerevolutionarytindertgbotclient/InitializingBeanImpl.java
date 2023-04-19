package ru.liga.prerevolutionarytindertgbotclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.prerevolutionarytindertgbotclient.botApi.PreRevolutionaryTinderBot;

@Component
@Slf4j
public class InitializingBeanImpl implements InitializingBean {
    private final PreRevolutionaryTinderBot bot;

    public InitializingBeanImpl(PreRevolutionaryTinderBot bot) {
        this.bot = bot;
    }
    @Override
    public void afterPropertiesSet() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
            log.info("Бот был успешно запущен");
        } catch (TelegramApiException | RuntimeException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
