package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileService;

@Component
public class UserProfileReadyQueryHandler implements CallbackQueryHandler {
    private final ReplyMessagesService replyMessagesService;
    private final UserDataCacheStore userDataCacheStore;

    private final UserProfileService userProfileService;

    public UserProfileReadyQueryHandler(ReplyMessagesService replyMessagesService, UserDataCacheStore userDataCacheStore, UserProfileService userProfileService) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCacheStore = userDataCacheStore;
        this.userProfileService = userProfileService;
    }

    public BotApiMethod<?> handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        long userId = callbackQuery.getFrom().getId();
        UserProfile userProfile = userDataCacheStore.getUserProfile(callbackQuery.getFrom().getId());
        if (data.equals(Gender.MALE.getName())) {
            userProfile.setSearch(Gender.MALE);
        }
        if (data.equals(Gender.FEMALE.getName())) {
            userProfile.setSearch(Gender.FEMALE);
        }
        System.out.println(data);
        if (data.equals("Всех")) {
            userProfile.setSearch(Gender.ALL);
        }
        userProfile.setUserId(userId);
        UserProfile profile = userProfileService.postUserProfile(userDataCacheStore.getUserProfile(userId));
        userDataCacheStore.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        return replyMessagesService.getReplyMessage(callbackQuery.getMessage().getChatId(), profile.toString(), userDataCacheStore.getUserCurrentBotState(callbackQuery.getMessage().getChatId()));
    }

    public BotState getHandlerName() {
        return BotState.USER_PROFILE_IS_READY;
    }
}