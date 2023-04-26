package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.StateHandler;

public interface MessageHandler extends StateHandler<Message> {
    PartialBotApiMethod<?> handle(Message message);
}
