package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandler;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

@Component
public class ShowUserProfileMessageHandler implements MessageHandler {
    private final UserDataCacheStore userDataCacheStore;
    private final ReplyMessagesService replyMessagesService;

    public ShowUserProfileMessageHandler(UserDataCacheStore userDataCacheStore, ReplyMessagesService replyMessagesService) {
        this.userDataCacheStore = userDataCacheStore;
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        UserProfile userProfile = userDataCacheStore.getUserProfile(message.getFrom().getId());
        return replyMessagesService.getReplyMessage(message.getChatId(), userProfile.toString(), userDataCacheStore.getUserCurrentBotState(message.getFrom().getId()));
        // Тут надо будет показывать профиль по запросу
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
