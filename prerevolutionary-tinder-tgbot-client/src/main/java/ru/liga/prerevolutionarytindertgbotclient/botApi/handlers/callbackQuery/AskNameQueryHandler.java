package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateService;

@Component
public class AskNameQueryHandler implements CallbackQueryHandler {
    private final ReplyMessagesService messagesService;
    private final UserDataCacheStore userDataCacheStore;
    private final UserStateService userStateService;

    public AskNameQueryHandler(ReplyMessagesService messagesService, UserDataCacheStore userDataCacheStore, UserStateService userStateService) {
        this.messagesService = messagesService;
        this.userDataCacheStore = userDataCacheStore;
        this.userStateService = userStateService;
    }
    @Override
    public PartialBotApiMethod<?> handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        UserProfile userProfile = userDataCacheStore.getUserProfile(callbackQuery.getFrom().getId());
        if (data.equals(Gender.MALE.getName())) {
            userProfile.setGender(Gender.MALE);
        }
        if (data.equals(Gender.FEMALE.getName())) {
            userProfile.setGender(Gender.FEMALE);
        }
        userStateService.updateUserState(callbackQuery.getFrom().getId().toString(), BotState.ASK_DESCRIPTION);
        return messagesService.getReplyMessage(callbackQuery.getMessage().getChatId(),
                "Как вас величать, " + userProfile.getGender().getName() + "?", BotState.ASK_DESCRIPTION);
    }
    @Override
    public BotState getHandlerName() {
        return BotState.ASK_NAME;
    }
}
