package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.List;

@Component
public class AskDescriptionHandler implements MessageHandler {
    private final UserDataCacheStore userDataCache;
    private final UserStateRestService userStateRestService;
    private final ReplyMessagesService messagesService;

    public AskDescriptionHandler(UserDataCacheStore userDataCache, UserStateRestService userStateRestService, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.userStateRestService = userStateRestService;
        this.messagesService = messagesService;
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        SendMessage replyToUser;
        long userId = message.getFrom().getId();
        String usersAnswer = message.getText();
        userDataCache.getUserProfile(userId).setName(usersAnswer);

        replyToUser = messagesService.getReplyMessage(message.getChatId(), "Извольте описать себя", BotState.ASK_PREFERENCES);
        userStateRestService.updateUserState(String.valueOf(userId), BotState.ASK_PREFERENCES);
        return List.of(replyToUser);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_DESCRIPTION;
    }
}
