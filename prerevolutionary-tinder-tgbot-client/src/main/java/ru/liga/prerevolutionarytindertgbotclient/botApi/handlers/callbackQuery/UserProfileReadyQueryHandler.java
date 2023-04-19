package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserProfileReadyQueryHandler implements CallbackQueryHandler {
    private final ReplyMessagesService replyMessagesService;
    private final UserDataCacheStore userDataCacheStore;

    public UserProfileReadyQueryHandler(ReplyMessagesService replyMessagesService, UserDataCacheStore userDataCacheStore) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCacheStore = userDataCacheStore;
    }
    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        UserProfile userProfile = userDataCacheStore.getUserProfile(callbackQuery.getFrom().getId());
        if (data.equals(Gender.MALE.getName())) {
            userProfile.setPreferences(List.of(Gender.MALE));
        }
        if (data.equals(Gender.FEMALE.getName())) {
            userProfile.setPreferences(List.of(Gender.FEMALE));
        }
        if (data.equals("All")) {
            userProfile.setPreferences(new ArrayList<>(Arrays.asList(Gender.values())));
        }
        userDataCacheStore.setUserCurrentBotState(callbackQuery.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        // тут сохранить и также показать профиль
        return replyMessagesService.getReplyMessage(callbackQuery.getMessage().getChatId(), userProfile.toString(), userDataCacheStore.getUserCurrentBotState(callbackQuery.getMessage().getChatId()));
    }
    public BotState getHandlerName() {
        return BotState.USER_PROFILE_IS_READY;
    }
}