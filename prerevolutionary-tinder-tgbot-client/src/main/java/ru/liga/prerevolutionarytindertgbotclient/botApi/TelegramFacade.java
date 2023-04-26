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
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileRestService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.List;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStageContext botStageContext;
    private final UserDataCacheStore userDataCache;
    private final UserStateRestService userStateRestService;

    public TelegramFacade(BotStageContext botStageContext, UserDataCacheStore userDataCache, UserProfileRestService userProfileRestService, UserStateRestService userStateRestService) {
        this.botStageContext = botStageContext;
        this.userDataCache = userDataCache;
        this.userStateRestService = userStateRestService;
    }

    public List<PartialBotApiMethod<?>> handleUpdate(Update update) {
        List<PartialBotApiMethod<?>> replyMessage = null;
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
        BotState botState = userStateRestService.getUserState(userId);
        if (botState == null) {
            userStateRestService.postUserState(userId, BotState.ASK_GENDER);
            return BotState.ASK_GENDER;
        }
        return botState;
    }
    private List<PartialBotApiMethod<?>> handleInputMessage(Message message) {
        long userId = message.getFrom().getId();
        BotState botState = getUserState(String.valueOf(userId));
        return botStageContext.processData(botState, message);
    }
    private List<PartialBotApiMethod<?>> handleCallbackQuery(CallbackQuery callbackQuery) {
        long userId = callbackQuery.getFrom().getId();
        BotState botState = getUserState(String.valueOf(userId));
        return botStageContext.processData(botState, callbackQuery);
    }
}
