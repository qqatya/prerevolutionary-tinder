package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message.MessageHandler;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

@Component
public class ShowSearchMessageHandler implements MessageHandler {
    private final ReplyMessagesService replyMessagesService;
    private static int pageCount = 1;
    private static final int PAGE_SIZE = 1;

    public ShowSearchMessageHandler(ReplyMessagesService replyMessagesService) {
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_SEARCH;
    }

    @Override
    public PartialBotApiMethod<?> handle(Message message) {
        // тут подключать сервис и выводить по одной анкетки
        SendMessage sendMessage = replyMessagesService.getReplyMessage(message.getChatId(), "Анкета номер " + pageCount, getHandlerName());
        pageCount++;
        return sendMessage;
    }
}
