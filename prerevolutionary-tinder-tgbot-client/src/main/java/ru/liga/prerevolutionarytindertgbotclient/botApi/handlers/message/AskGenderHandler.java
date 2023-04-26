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
public class AskGenderHandler implements MessageHandler {
    private final ReplyMessagesService messagesService;
    private final UserDataCacheStore userDataCacheStore;
    private final UserStateService userStateService;

    public AskGenderHandler(ReplyMessagesService messagesService, UserDataCacheStore userDataCacheStore, UserStateService userStateService) {
        this.messagesService = messagesService;
        this.userDataCacheStore = userDataCacheStore;
        this.userStateService = userStateService;
    }

    @Override
    public PartialBotApiMethod<?> handle(Message message) {

        SendMessage replyToUser = messagesService.getReplyMessage(message.getChatId(), "Вы сударь иль сударыня?", BotState.ASK_NAME);
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        userStateService.updateUserState(message.getFrom().getId().toString(), BotState.ASK_NAME);
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
