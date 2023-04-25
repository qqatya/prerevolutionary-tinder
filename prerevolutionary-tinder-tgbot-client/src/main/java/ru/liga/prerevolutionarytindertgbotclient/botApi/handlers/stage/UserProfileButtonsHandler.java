package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;

@Service
public class UserProfileButtonsHandler implements StageHandler {
    private final MessageHandlersFactory messageHandlersFactory;
    private final UserDataCacheStore userDataCacheStore;

    public UserProfileButtonsHandler(MessageHandlersFactory messageHandlersFactory, UserDataCacheStore userDataCacheStore) {
        this.messageHandlersFactory = messageHandlersFactory;
        this.userDataCacheStore = userDataCacheStore;
    }

    @Override
    public BotApiMethod<?> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject + " : " + this);
    }

    private BotApiMethod<?> handleMessage(Message message) {
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        if (messageText.equals("Изменить")) {
            userDataCacheStore.setUserCurrentBotState(userId, BotState.ASK_GENDER);
            return messageHandlersFactory.getHandler(BotState.ASK_GENDER).handle(message);
        }
        if (messageText.equals("Выйти в меню")) {
            userDataCacheStore.setUserCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
            return messageHandlersFactory.getHandler(BotState.SHOW_USER_PROFILE).handle(message);
        }
        throw new RuntimeException("Странное сообщение от бота...");
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.USER_PROFILE;
    }
}
