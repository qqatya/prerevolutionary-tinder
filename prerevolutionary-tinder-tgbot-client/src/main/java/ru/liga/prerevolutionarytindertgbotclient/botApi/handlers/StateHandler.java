package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

public interface StateHandler<T extends BotApiObject> {
    PartialBotApiMethod<?> handle(T apiObject);

    BotState getHandlerName();
}
