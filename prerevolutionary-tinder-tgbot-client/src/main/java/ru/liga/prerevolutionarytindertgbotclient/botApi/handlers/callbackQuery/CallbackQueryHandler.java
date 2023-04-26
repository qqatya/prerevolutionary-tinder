package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.StateHandler;

public interface CallbackQueryHandler extends StateHandler<CallbackQuery> {
    PartialBotApiMethod<?> handle(CallbackQuery callbackQuery);
}
