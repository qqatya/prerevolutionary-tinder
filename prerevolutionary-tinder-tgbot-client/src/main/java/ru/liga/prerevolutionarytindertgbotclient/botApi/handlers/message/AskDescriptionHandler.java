package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

@Component
public class AskDescriptionHandler implements MessageHandler {
    private final UserDataCacheStore userDataCache;
    private final ReplyMessagesService messagesService;

    public AskDescriptionHandler(UserDataCacheStore userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        SendMessage replyToUser;
        long userId = message.getFrom().getId();
        String usersAnswer = message.getText();
        userDataCache.getUserProfile(userId).setName(usersAnswer);
        userDataCache.setUserCurrentBotState(userId, BotState.ASK_PREFERENCES);
        replyToUser = messagesService.getReplyMessage(message.getChatId(), "Извольте описать себя", userDataCache.getUserCurrentBotState(userId));
        return replyToUser;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_DESCRIPTION;
    }
}
