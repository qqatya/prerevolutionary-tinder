package ru.liga.prerevolutionarytindertgbotclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import static ru.liga.prerevolutionarytindertgbotclient.utils.BotStateUtils.getBotCurrentStage;

@Service
@Slf4j
public class ReplyMessagesService {
    private final KeyboardGeneratorService keyboardGeneratorService;

    public ReplyMessagesService(KeyboardGeneratorService keyboardGeneratorService) {
        this.keyboardGeneratorService = keyboardGeneratorService;
    }

    public SendMessage getReplyMessage(long chatId, String message, BotState botState) {
        BotStage currentStage = getBotCurrentStage(botState);
        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), message);
        if (currentStage == BotStage.MAIN_MENU) {
            replyMessage.setReplyMarkup(keyboardGeneratorService.getMainMenuKeyboard());
            return replyMessage;
        }
        if (currentStage == BotStage.FAVORITES || currentStage == BotStage.SEARCH) {
            replyMessage.setReplyMarkup(keyboardGeneratorService.getTinderFunctionsKeyboard());
            return replyMessage;
        }
        if (currentStage == BotStage.USER_PROFILE) {
            replyMessage.setReplyMarkup(keyboardGeneratorService.getShowProfileKeyboard());
            return replyMessage;
        }
        replyMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        log.info("Было сформированно ответное сообщение для чата " + chatId + ": " + replyMessage);
        return replyMessage;
    }
}
