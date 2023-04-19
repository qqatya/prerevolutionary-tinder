package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MessageHandlersFactory {
    private final Map<BotState, MessageHandler> messageHandlers = new HashMap<>();

    public MessageHandlersFactory(List<MessageHandler> stageHandlers) {
        for (MessageHandler messageHandler : stageHandlers) {
            this.messageHandlers.put(messageHandler.getHandlerName(), messageHandler);
        }
    }

    public MessageHandler getHandler(BotState botState) {
        MessageHandler messageHandler = messageHandlers.get(botState);
        if (messageHandler == null) {
            throw new RuntimeException("Не найдет обработчик для текущего состояния" + botState + "объекта типа Message");
        }
        log.debug("Найдет обработчик для текущего состояния " + botState + "объекта типа Message" + messageHandler);
        return messageHandler;
    }
}
