package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CallbackQueryHandlersFactory {
    private final Map<BotState, CallbackQueryHandler> handlers = new HashMap<>();
    public CallbackQueryHandlersFactory(List<CallbackQueryHandler> handlers) {
        for (CallbackQueryHandler handler : handlers) {
            this.handlers.put(handler.getHandlerName(), handler);
        }
    }
    public CallbackQueryHandler getHandler(BotState botState) {
        CallbackQueryHandler handler = handlers.get(botState);
        if (handler == null) {
            throw new RuntimeException("Не найдет обработчик для текущего состояния" + botState + "объекта типа CallbackQuery");
        }
        log.debug("Найдет обработчик для текущего состояния " + botState + "объекта типа CallbackQuery" + handler);
        return handler;
    }
}
