package ru.liga.prerevolutionarytindertgbotclient.service;

import com.google.common.io.ByteSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

import java.io.IOException;

import static ru.liga.prerevolutionarytindertgbotclient.utils.BotStateUtils.getBotCurrentStage;

@Service
@Slf4j
public class ReplyMessagesService {
    private final KeyboardGeneratorService keyboardGeneratorService;

    public ReplyMessagesService(KeyboardGeneratorService keyboardGeneratorService) {
        this.keyboardGeneratorService = keyboardGeneratorService;
    }

    public SendPhoto getPhotoMessage(long chatId, byte[] imageByteArray, BotState botState) {
        SendPhoto replyPhoto;
        try {
            replyPhoto = new SendPhoto(String.valueOf(chatId), new InputFile(ByteSource.wrap(imageByteArray).openStream(), "Портфолио"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        replyPhoto.setReplyMarkup(getStageKeyboard(botState));
        return replyPhoto;
    }

    public SendMessage getReplyMessage(long chatId, String message, BotState botState) {
        SendMessage replyMessage = new SendMessage(String.valueOf(chatId), message);
        replyMessage.setReplyMarkup(getStageKeyboard(botState));
        log.info("Было сформированно ответное сообщение для чата " + chatId + ": " + replyMessage);
        return replyMessage;
    }

    private ReplyKeyboard getStageKeyboard(BotState botState) {
        BotStage currentStage = getBotCurrentStage(botState);
        if (currentStage == BotStage.MAIN_MENU) {
            return keyboardGeneratorService.getMainMenuKeyboard();
        }
        if (currentStage == BotStage.FAVORITES || currentStage == BotStage.SEARCH) {
            return keyboardGeneratorService.getTinderFunctionsKeyboard();
        }
        if (currentStage == BotStage.USER_PROFILE) {
            return keyboardGeneratorService.getShowProfileKeyboard();
        }
        return new ReplyKeyboardRemove(true);
    }
}
