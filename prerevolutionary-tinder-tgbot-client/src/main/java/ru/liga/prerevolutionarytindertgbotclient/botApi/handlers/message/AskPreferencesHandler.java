package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.repository.UserDataCacheStore;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateService;

import java.util.List;
@Component
public class AskPreferencesHandler implements MessageHandler {
    private final UserDataCacheStore userDataCache;
    private final UserStateService userStateService;
    private final ReplyMessagesService messagesService;

    public AskPreferencesHandler(UserDataCacheStore userDataCache, UserStateService userStateService, ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.userStateService = userStateService;
        this.messagesService = messagesService;
    }

    @Override
    public PartialBotApiMethod<?> handle(Message message) {
        SendMessage replyToUser;
        long userId = message.getFrom().getId();
        userDataCache.getUserProfile(userId).setDescription(message.getText());
        replyToUser = messagesService.getReplyMessage(message.getChatId(), "Кого вы ищите?", BotState.USER_PROFILE_IS_READY);
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        userStateService.updateUserState(String.valueOf(userId), BotState.USER_PROFILE_IS_READY);
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
        buttonAll.setCallbackData("Всех");
        List<List<InlineKeyboardButton>> buttonList = List.of(List.of(buttonMan, buttonWoman), List.of(buttonAll));
        inlineKeyboardMarkup.setKeyboard(buttonList);
        return inlineKeyboardMarkup;
    }
    @Override
    public BotState getHandlerName() {
        return BotState.ASK_PREFERENCES;
    }
}
