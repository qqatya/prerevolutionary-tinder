package ru.liga.prerevolutionarytindertgbotclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.utils.BotStateUtils;

@Service
@Slf4j
public class ReplyMessagesService {
    private final MainMenuService mainMenuService;

    public ReplyMessagesService(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    public SendMessage getReplyMessage(long chatId, String message, BotState botState) {
        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), message);
        if (BotStateUtils.getBotCurrentStage(botState) == BotStage.MAIN_MENU) {
            replyMessage.setReplyMarkup(mainMenuService.getMainMenuKeyboard());
        }
        log.info("Было сформированно ответное сообщение для чата " + chatId + ": " + replyMessage);
        return replyMessage;
    }
}
