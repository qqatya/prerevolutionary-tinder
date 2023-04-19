package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

public interface StateHandler<T extends BotApiObject> {
    BotApiMethod<?> handle(T apiObject);

    BotState getHandlerName();
}
