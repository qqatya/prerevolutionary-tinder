package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindercommon.enums.Gender;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.List;

@Component
public class AskNameQueryHandler implements CallbackQueryHandler {
    private final ReplyMessagesService messagesService;
    private final UserDataCacheStore userDataCacheStore;
    private final UserStateRestService userStateRestService;

    public AskNameQueryHandler(ReplyMessagesService messagesService, UserDataCacheStore userDataCacheStore, UserStateRestService userStateRestService) {
        this.messagesService = messagesService;
        this.userDataCacheStore = userDataCacheStore;
        this.userStateRestService = userStateRestService;
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        UserProfile userProfile = userDataCacheStore.getUserProfile(callbackQuery.getFrom().getId());
        if (data.equals(Gender.MALE.getName())) {
            userProfile.setGender(Gender.MALE);
        }
        if (data.equals(Gender.FEMALE.getName())) {
            userProfile.setGender(Gender.FEMALE);
        }
        userStateRestService.updateUserState(callbackQuery.getFrom().getId().toString(), BotState.ASK_DESCRIPTION);
        return List.of(messagesService.getReplyMessage(callbackQuery.getMessage().getChatId(),
                "Как вас величать, " + userProfile.getGender().getName() + "?", BotState.ASK_DESCRIPTION));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_NAME;
    }
}
