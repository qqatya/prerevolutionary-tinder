package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.liga.prerevolutionarytindertgbotclient.utils.BotStateUtils.getBotCurrentStage;

@Component
@Slf4j
public class StageHandlersFactory {
    private final Map<BotStage, StageHandler> stageHandlers = new HashMap<>();

    public StageHandlersFactory(List<StageHandler> stageHandlers) {
        for (StageHandler stageHandler : stageHandlers) {
            this.stageHandlers.put(stageHandler.getHandlerName(), stageHandler);
        }
    }

    public StageHandler getHandler(BotState botState) {
        BotStage currentStage = getBotCurrentStage(botState);
        StageHandler stageHandler = stageHandlers.get(currentStage);
        if (stageHandler == null) {
            throw new RuntimeException("Не найдет обработчик для текущей стадии " + currentStage);
        }
        log.debug("Найдет обработчик для текущей стадии " + currentStage + ": " +  stageHandler);
        return stageHandler;
    }
}
