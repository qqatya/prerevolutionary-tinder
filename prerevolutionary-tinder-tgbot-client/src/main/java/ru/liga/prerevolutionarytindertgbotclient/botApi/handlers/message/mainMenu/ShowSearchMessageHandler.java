package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandler;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

@Component
public class ShowSearchMessageHandler implements MessageHandler {
    private final ReplyMessagesService replyMessagesService;

    public ShowSearchMessageHandler(ReplyMessagesService replyMessagesService) {
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_SEARCH;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return replyMessagesService.getReplyMessage(message.getChatId(), "Not yet implemented", getHandlerName());
    }
}
