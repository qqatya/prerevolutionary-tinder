package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandler;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

import java.util.List;

@Component
public class ShowFavoritesMessageHandler implements MessageHandler {
    private final ReplyMessagesService replyMessagesService;

    public ShowFavoritesMessageHandler(ReplyMessagesService replyMessagesService) {
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_FAVORITES;
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {
        return List.of(replyMessagesService.getReplyMessage(message.getChatId(), "Not yet implemented", getHandlerName()));
    }
}
