package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;

import java.util.List;

@Component
public class AskGenderHandler implements MessageHandler {
    private final ReplyMessagesService messagesService;
    private final UserDataCacheStore userDataCacheStore;

    public AskGenderHandler(ReplyMessagesService messagesService, UserDataCacheStore userDataCacheStore) {
        this.messagesService = messagesService;
        this.userDataCacheStore = userDataCacheStore;
    }

    @Override
    public SendMessage handle(Message message) {
        userDataCacheStore.setUserCurrentBotState(message.getFrom().getId(), BotState.ASK_NAME);
        SendMessage replyToUser = messagesService.getReplyMessage(message.getChatId(), "Вы сударь иль сударыня?", userDataCacheStore.getUserCurrentBotState(message.getFrom().getId()));
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        return replyToUser;
    }
    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonMan = new InlineKeyboardButton();
        buttonMan.setText("Сударь");
        buttonMan.setCallbackData(Gender.MALE.getName());
        InlineKeyboardButton buttonWoman = new InlineKeyboardButton();
        buttonWoman.setText("Сударыня");
        buttonWoman.setCallbackData(Gender.FEMALE.getName());
        List<List<InlineKeyboardButton>> buttonList = List.of(List.of(buttonMan, buttonWoman));
        inlineKeyboardMarkup.setKeyboard(buttonList);
        return inlineKeyboardMarkup;
    }
    @Override
    public BotState getHandlerName() {
        return BotState.ASK_GENDER;
    }
}
