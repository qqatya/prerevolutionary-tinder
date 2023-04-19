package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

public interface StageHandler {
    BotApiMethod<?> handle(BotApiObject apiObject, BotState botState);
    BotStage getHandlerName();
}
