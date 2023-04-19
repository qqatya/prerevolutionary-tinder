package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.callbackQuery;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.StateHandler;

public interface CallbackQueryHandler extends StateHandler<CallbackQuery> {
    BotApiMethod<?> handle(CallbackQuery callbackQuery);
}
