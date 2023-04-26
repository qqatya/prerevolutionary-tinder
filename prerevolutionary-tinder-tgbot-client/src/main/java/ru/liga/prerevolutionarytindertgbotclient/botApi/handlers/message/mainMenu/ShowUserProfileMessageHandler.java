package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandler;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileService;

@Component
public class ShowUserProfileMessageHandler implements MessageHandler {
    private final UserDataCacheStore userDataCacheStore;
    private final ReplyMessagesService replyMessagesService;
    private final UserProfileService userProfileService;

    public ShowUserProfileMessageHandler(UserDataCacheStore userDataCacheStore, ReplyMessagesService replyMessagesService, UserProfileService userProfileService) {
        this.userDataCacheStore = userDataCacheStore;
        this.replyMessagesService = replyMessagesService;
        this.userProfileService = userProfileService;
    }

    @Override
    public PartialBotApiMethod<?> handle(Message message) {
        byte[] userImage = userProfileService.getUserImage(message.getFrom().getId());
        return replyMessagesService.getPhotoMessage(message.getChatId(), userImage, BotState.SHOW_USER_PROFILE);
        // Тут надо будет показывать профиль по запросу
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
