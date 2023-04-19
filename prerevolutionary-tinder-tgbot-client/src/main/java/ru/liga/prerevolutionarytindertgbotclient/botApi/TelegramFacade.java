package ru.liga.prerevolutionarytindertgbotclient.botApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.BotStageContext;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStageContext botStageContext;
    private final UserDataCacheStore userDataCache;

    public TelegramFacade(BotStageContext botStageContext, UserDataCacheStore userDataCache) {
        this.botStageContext = botStageContext;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return handleCallbackQuery(callbackQuery);

        }
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            System.out.println("Попадаю сюда");
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }
    private BotApiMethod<?> handleInputMessage(Message message) {
        long userId = message.getFrom().getId();
        BotState botState = userDataCache.getUserCurrentBotState(userId);
        if (botState == null) {
            userDataCache.setUserCurrentBotState(userId, BotState.ASK_GENDER);
        }
        return botStageContext.processData(botState, message);
    }
    private BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        long userId = callbackQuery.getFrom().getId();
        BotState botState = userDataCache.getUserCurrentBotState(userId);
        return botStageContext.processData(botState, callbackQuery);
    }
}
