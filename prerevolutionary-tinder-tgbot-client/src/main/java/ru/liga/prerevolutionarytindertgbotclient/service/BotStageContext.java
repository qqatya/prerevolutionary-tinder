package ru.liga.prerevolutionarytindertgbotclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage.StageHandlersFactory;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

@Component
@Slf4j
public class BotStageContext {
    private final StageHandlersFactory stageHandlersFactory;
    public BotStageContext(StageHandlersFactory stageHandlersFactory) {
        this.stageHandlersFactory = stageHandlersFactory;

    }

    public BotApiMethod<?> processData(BotState currentState, BotApiObject message) {
        log.debug("Текущее состояние юзера: " + currentState);
        return stageHandlersFactory.getHandler(currentState).handle(message, currentState);
    }
}
