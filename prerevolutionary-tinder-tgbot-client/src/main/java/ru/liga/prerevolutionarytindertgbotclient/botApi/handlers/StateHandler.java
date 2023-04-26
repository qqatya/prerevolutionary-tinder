package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

import java.util.List;

public interface StateHandler<T extends BotApiObject> {
    List<PartialBotApiMethod<?>> handle(T apiObject);

    BotState getHandlerName();
}
