package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

public interface StageHandler {
    PartialBotApiMethod<?> handle(BotApiObject apiObject, BotState botState);
    BotStage getHandlerName();
}
