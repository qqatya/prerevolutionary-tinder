package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileRestService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.List;

@Component
public class UserProfileReadyQueryHandler implements CallbackQueryHandler {
    private final ReplyMessagesService replyMessagesService;
    private final UserDataCacheStore userDataCacheStore;

    private final UserProfileRestService userProfileRestService;
    private final UserStateRestService userStateRestService;

    public UserProfileReadyQueryHandler(ReplyMessagesService replyMessagesService, UserDataCacheStore userDataCacheStore, UserProfileRestService userProfileRestService, UserStateRestService userStateRestService) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCacheStore = userDataCacheStore;
        this.userProfileRestService = userProfileRestService;
        this.userStateRestService = userStateRestService;
    }

    public List<PartialBotApiMethod<?>> handle(CallbackQuery callbackQuery) {
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
        userProfileRestService.postUserProfile(userDataCacheStore.getUserProfile(userId));
        byte[] image = userProfileRestService.getUserImage(userId);
        userStateRestService.updateUserState(String.valueOf(userId), BotState.SHOW_MAIN_MENU);
        return List.of(replyMessagesService.getPhotoMessage(callbackQuery.getMessage().getChatId(), image, BotState.SHOW_MAIN_MENU));
    }

    public BotState getHandlerName() {
        return BotState.USER_PROFILE_IS_READY;
    }
}