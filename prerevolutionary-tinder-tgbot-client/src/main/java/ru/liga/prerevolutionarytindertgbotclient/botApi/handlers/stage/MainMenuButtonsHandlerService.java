package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

@Component
public class MainMenuButtonsHandlerService implements StageHandler {
    private final MessageHandlersFactory messageHandlersFactory;

    public MainMenuButtonsHandlerService(MessageHandlersFactory messageHandlersFactory) {
        this.messageHandlersFactory = messageHandlersFactory;
    }

    public BotApiMethod<?> handleMessage(Message message) {
        String messageText = message.getText();
        if (messageText.equals("Анкета")) {
            return messageHandlersFactory.getHandler(BotState.SHOW_USER_PROFILE).handle(message);
        }
        if (messageText.equals("Любимцы")) {
            return messageHandlersFactory.getHandler(BotState.SHOW_FAVORITES).handle(message);
        }
        if (messageText.equals("Поиск")) {
            return messageHandlersFactory.getHandler(BotState.SHOW_SEARCH).handle(message);
        }
        throw new RuntimeException("Странное сообщение от бота в главном меню...");
    }

    public BotApiMethod<?> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject);
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.MAIN_MENU;
    }
}