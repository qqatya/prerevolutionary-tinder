package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateService;

@Component
public class MainMenuButtonsHandler implements StageHandler {
    private final MessageHandlersFactory messageHandlersFactory;
    private final UserStateService userStateService;

    public MainMenuButtonsHandler(MessageHandlersFactory messageHandlersFactory, UserStateService userStateService) {
        this.messageHandlersFactory = messageHandlersFactory;
        this.userStateService = userStateService;
    }

    public PartialBotApiMethod<?> handleMessage(Message message) {
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        if (messageText.equals("Анкета")) {
            userStateService.updateUserState(String.valueOf(userId), BotState.SHOW_USER_PROFILE);
            return messageHandlersFactory.getHandler(BotState.SHOW_USER_PROFILE).handle(message);
        }
        if (messageText.equals("Любимцы")) {
            userStateService.updateUserState(String.valueOf(userId), BotState.SHOW_FAVORITES);
            return messageHandlersFactory.getHandler(BotState.SHOW_FAVORITES).handle(message);
        }
        if (messageText.equals("Поиск")) {
            userStateService.updateUserState(String.valueOf(userId), BotState.SHOW_SEARCH);
            return messageHandlersFactory.getHandler(BotState.SHOW_SEARCH).handle(message);
        }
        throw new RuntimeException("Странное сообщение от бота в главном меню...");
    }

    public PartialBotApiMethod<?> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject + " : " + this);
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.MAIN_MENU;
    }
}