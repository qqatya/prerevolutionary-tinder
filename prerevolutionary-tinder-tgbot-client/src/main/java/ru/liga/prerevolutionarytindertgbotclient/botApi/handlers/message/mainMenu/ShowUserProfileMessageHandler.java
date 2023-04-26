package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandler;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileRestService;

import java.util.List;

@Component
public class ShowUserProfileMessageHandler implements MessageHandler {
    private final ReplyMessagesService replyMessagesService;
    private final UserProfileRestService userProfileRestService;

    public ShowUserProfileMessageHandler(ReplyMessagesService replyMessagesService, UserProfileRestService userProfileRestService) {
        this.replyMessagesService = replyMessagesService;
        this.userProfileRestService = userProfileRestService;
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        byte[] userImage = userProfileRestService.getUserImage(message.getFrom().getId());
        return List.of(replyMessagesService.getPhotoMessage(message.getChatId(), userImage, BotState.SHOW_USER_PROFILE));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
