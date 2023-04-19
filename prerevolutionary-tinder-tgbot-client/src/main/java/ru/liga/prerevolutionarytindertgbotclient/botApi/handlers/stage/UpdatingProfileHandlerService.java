package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery.CallbackQueryHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;

@Slf4j
@Component
public class UpdatingProfileHandlerService implements StageHandler {
    private final UserDataCacheStore userDataCache;
    private final MessageHandlersFactory messageHandlersFactory;
    private final CallbackQueryHandlersFactory queryHandlersFactory;

    public UpdatingProfileHandlerService(UserDataCacheStore userDataCache, @Lazy MessageHandlersFactory messageHandlersFactory, @Lazy CallbackQueryHandlersFactory queryHandlersFactory) {
        this.userDataCache = userDataCache;
        this.messageHandlersFactory = messageHandlersFactory;
        this.queryHandlersFactory = queryHandlersFactory;
    }

    public BotApiMethod<?> handleMessage(Message message, BotState botState) {
        long userId = message.getFrom().getId();
        resetBotStateIfNeeded(userId);
        BotApiMethod<?> replyToUser = messageHandlersFactory.getHandler(botState).handle(message);
        userDataCache.saveUserProfile(userId, userDataCache.getUserProfile(userId));
        return replyToUser;
    }

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery, BotState botState) {
        long userId = callbackQuery.getFrom().getId();
        resetBotStateIfNeeded(userId);
        BotApiMethod<?> replyToUser = queryHandlersFactory.getHandler(botState).handle(callbackQuery);
        userDataCache.saveUserProfile(userId, userDataCache.getUserProfile(userId));
        return replyToUser;
    }

    public void resetBotStateIfNeeded(long userId) {
        BotState currentBotState = userDataCache.getUserCurrentBotState(userId);
        if (currentBotState.equals(BotState.UPDATING_USER_PROFILE)) {
            userDataCache.setUserCurrentBotState(userId, BotState.ASK_GENDER);
        }
    }

    @Override
    public BotApiMethod<?> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject, botState);
        }
        if (apiObject instanceof CallbackQuery) {
            return handleCallbackQuery((CallbackQuery) apiObject, botState);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject);
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.UPDATING_PROFILE;
    }
}
