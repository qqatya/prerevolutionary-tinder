package ru.liga.prerevolutionarytindertgbotclient.botApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.BotStageContext;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateService;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStageContext botStageContext;
    private final UserDataCacheStore userDataCache;
    private final UserStateService userStateService;

    public TelegramFacade(BotStageContext botStageContext, UserDataCacheStore userDataCache, UserProfileService userProfileService, UserStateService userStateService) {
        this.botStageContext = botStageContext;
        this.userDataCache = userDataCache;
        this.userStateService = userStateService;
    }

    public PartialBotApiMethod<?> handleUpdate(Update update) {
        PartialBotApiMethod<?> replyMessage = null;
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
    public BotState getUserState(String userId) {
        BotState botState = userStateService.getUserState(userId);
        if (botState == null) {
            userStateService.postUserState(userId, BotState.ASK_GENDER);
            return BotState.ASK_GENDER;
        }
        return botState;
    }
    private PartialBotApiMethod<?> handleInputMessage(Message message) {
        long userId = message.getFrom().getId();
        BotState botState = getUserState(String.valueOf(userId));
        return botStageContext.processData(botState, message);
    }
    private PartialBotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        long userId = callbackQuery.getFrom().getId();
        BotState botState = getUserState(String.valueOf(userId));
        return botStageContext.processData(botState, callbackQuery);
    }
}
