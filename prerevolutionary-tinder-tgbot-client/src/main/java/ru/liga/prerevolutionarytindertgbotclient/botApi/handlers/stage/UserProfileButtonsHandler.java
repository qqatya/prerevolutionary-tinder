package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateService;

@Service
public class UserProfileButtonsHandler implements StageHandler {
    private final MessageHandlersFactory messageHandlersFactory;
    private final UserStateService userStateService;
    private final ReplyMessagesService replyMessagesService;

    public UserProfileButtonsHandler(MessageHandlersFactory messageHandlersFactory, UserStateService userStateService, ReplyMessagesService replyMessagesService) {
        this.messageHandlersFactory = messageHandlersFactory;
        this.userStateService = userStateService;
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public PartialBotApiMethod<?> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject + " : " + this);
    }

    private PartialBotApiMethod<?> handleMessage(Message message) {
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        if (messageText.equals("Изменить")) {
            userStateService.updateUserState(String.valueOf(userId), BotState.ASK_GENDER);
            return messageHandlersFactory.getHandler(BotState.ASK_GENDER).handle(message);
        }
        if (messageText.equals("Выйти в меню")) {
            userStateService.updateUserState(String.valueOf(userId), BotState.SHOW_MAIN_MENU);
            return replyMessagesService.getReplyMessage(message.getChatId(), "Главное меню", BotState.SHOW_MAIN_MENU);
        }
        throw new RuntimeException("Странное сообщение от бота...");
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.USER_PROFILE;
    }
}
