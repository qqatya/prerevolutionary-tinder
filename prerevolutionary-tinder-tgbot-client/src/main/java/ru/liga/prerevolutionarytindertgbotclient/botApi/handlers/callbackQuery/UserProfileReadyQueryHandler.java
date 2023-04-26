package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateService;

@Component
public class UserProfileReadyQueryHandler implements CallbackQueryHandler {
    private final ReplyMessagesService replyMessagesService;
    private final UserDataCacheStore userDataCacheStore;

    private final UserProfileService userProfileService;
    private final UserStateService userStateService;

    public UserProfileReadyQueryHandler(ReplyMessagesService replyMessagesService, UserDataCacheStore userDataCacheStore, UserProfileService userProfileService, UserStateService userStateService) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCacheStore = userDataCacheStore;
        this.userProfileService = userProfileService;
        this.userStateService = userStateService;
    }

    public PartialBotApiMethod<?> handle(CallbackQuery callbackQuery) {
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
        userProfileService.postUserProfile(userDataCacheStore.getUserProfile(userId));
        byte[] image = userProfileService.getUserImage(userId);
        userStateService.updateUserState(String.valueOf(userId), BotState.SHOW_MAIN_MENU);
        return replyMessagesService.getPhotoMessage(callbackQuery.getMessage().getChatId(), image, BotState.SHOW_MAIN_MENU);
    }

    public BotState getHandlerName() {
        return BotState.USER_PROFILE_IS_READY;
    }
}