package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.Gender;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.List;

@Component
public class AskGenderHandler implements MessageHandler {
    private final ReplyMessagesService messagesService;
    private final UserStateRestService userStateRestService;

    public AskGenderHandler(ReplyMessagesService messagesService, UserStateRestService userStateRestService) {
        this.messagesService = messagesService;
        this.userStateRestService = userStateRestService;
    }

    @Override
    public List<PartialBotApiMethod<?>> handle(Message message) {

        SendMessage replyToUser = messagesService.getReplyMessage(message.getChatId(), "Вы сударь иль сударыня?", BotState.ASK_NAME);
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        userStateRestService.updateUserState(message.getFrom().getId().toString(), BotState.ASK_NAME);
        return List.of(replyToUser);
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
