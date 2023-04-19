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
public class AskPreferencesHandler implements MessageHandler {
    private final UserDataCacheStore userDataCache;
    private final ReplyMessagesService messagesService;

    public AskPreferencesHandler(UserDataCacheStore userDataCache, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        SendMessage replyToUser;
        long userId = message.getFrom().getId();
        userDataCache.getUserProfile(userId).setDescription(message.getText());
        userDataCache.setUserCurrentBotState(userId, BotState.USER_PROFILE_IS_READY);
        replyToUser = messagesService.getReplyMessage(message.getChatId(), "Кого вы ищите?", userDataCache.getUserCurrentBotState(userId));
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        return replyToUser;
    }
        private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonMan = new InlineKeyboardButton();
        buttonMan.setText(Gender.MALE.getName());
        buttonMan.setCallbackData(Gender.MALE.getName());
        InlineKeyboardButton buttonWoman = new InlineKeyboardButton();
        buttonWoman.setText(Gender.FEMALE.getName());
        buttonWoman.setCallbackData(Gender.FEMALE.getName());
        InlineKeyboardButton buttonAll = new InlineKeyboardButton();
        buttonAll.setText("Всех");
        buttonAll.setCallbackData("All");
        List<List<InlineKeyboardButton>> buttonList = List.of(List.of(buttonMan, buttonWoman), List.of(buttonAll));
        inlineKeyboardMarkup.setKeyboard(buttonList);
        return inlineKeyboardMarkup;
    }
    @Override
    public BotState getHandlerName() {
        return BotState.ASK_PREFERENCES;
    }
}
