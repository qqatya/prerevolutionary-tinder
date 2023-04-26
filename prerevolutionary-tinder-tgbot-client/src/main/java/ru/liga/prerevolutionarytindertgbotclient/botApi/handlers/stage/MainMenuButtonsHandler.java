package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.List;

@Component
public class MainMenuButtonsHandler implements StageHandler {
    private final MessageHandlersFactory messageHandlersFactory;
    private final UserStateRestService userStateRestService;
    private final FavoritesMenuButtonsHandler favoritesMenuButtonsHandler;
    private final SearchMenuButtonsHandler searchMenuButtonsHandler;

    public MainMenuButtonsHandler(MessageHandlersFactory messageHandlersFactory, UserStateRestService userStateRestService, FavoritesMenuButtonsHandler favoritesMenuButtonsHandler, SearchMenuButtonsHandler searchMenuButtonsHandler) {
        this.messageHandlersFactory = messageHandlersFactory;
        this.userStateRestService = userStateRestService;
        this.favoritesMenuButtonsHandler = favoritesMenuButtonsHandler;
        this.searchMenuButtonsHandler = searchMenuButtonsHandler;
    }

    public List<PartialBotApiMethod<?>> handleMessage(Message message) {
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        if (messageText.equals("Анкета")) {
            userStateRestService.updateUserState(String.valueOf(userId), BotState.SHOW_USER_PROFILE);
            return messageHandlersFactory.getHandler(BotState.SHOW_USER_PROFILE).handle(message);
        }
        if (messageText.equals("Любимцы")) {
            userStateRestService.updateUserState(String.valueOf(userId), BotState.SHOW_FAVORITES);
            return favoritesMenuButtonsHandler.handleMessage(message);
        }
        if (messageText.equals("Поиск")) {
            userStateRestService.updateUserState(String.valueOf(userId), BotState.SHOW_SEARCH);
            return searchMenuButtonsHandler.handleMessage(message);
        }
        throw new RuntimeException("Странное сообщение от бота в главном меню...");
    }

    public List<PartialBotApiMethod<?>> handle(BotApiObject apiObject, BotState botState) {
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