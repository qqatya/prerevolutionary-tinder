package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.service.ReplyMessagesService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserProfileRestService;
import ru.liga.prerevolutionarytindertgbotclient.service.rest.UserStateRestService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SearchMenuButtonsHandler implements StageHandler {
    private final int PAGE_SIZE = 1;
    private int pageCount = 0;
    private int totalElements = 0;
    private final UserStateRestService userStateRestService;
    private final ReplyMessagesService replyMessagesService;
    private final UserProfileRestService userProfileRestService;

    public SearchMenuButtonsHandler(UserStateRestService userStateRestService, ReplyMessagesService replyMessagesService, UserProfileRestService userProfileRestService) {
        this.userStateRestService = userStateRestService;
        this.replyMessagesService = replyMessagesService;
        this.userProfileRestService = userProfileRestService;
    }

    public List<PartialBotApiMethod<?>> handleMessage(Message message) {
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        if (messageText.equals("Влево")) {
            return handleSwipe(message, false);

        }
        if (messageText.equals("Вправо")) {
            return handleSwipe(message, true);
        }
        if (messageText.equals("Меню")) {
            userStateRestService.updateUserState(String.valueOf(userId), BotState.SHOW_MAIN_MENU);
            return List.of(replyMessagesService.getReplyMessage(message.getChatId(), "Главное меню", BotState.SHOW_MAIN_MENU));
        }
        if (messageText.equals("Поиск")) {
            return handleSwipe(message, false);
        }
        throw new RuntimeException("Странное сообщение от бота в главном меню...");
    }

    private List<PartialBotApiMethod<?>> handleSwipe(Message message, boolean isLiked) {
        long userId = message.getFrom().getId();
        JsonNode userData = userProfileRestService.searchProfiles(String.valueOf(userId), pageCount, PAGE_SIZE);
        JsonNode content = userData.get("content");
        totalElements = userData.get("totalElements").asInt();
        byte[] userProfileImage = userProfileRestService.getUserImage(content.get(0).get("userId").asLong());
        pageCount++;
        if (totalElements <= pageCount) {
            pageCount = 0;
        }
        List<PartialBotApiMethod<?>> replies = new ArrayList<>();
        replies.add(replyMessagesService.getPhotoMessage(message.getChatId(), userProfileImage, BotState.SHOW_SEARCH));
        if (isLiked) {
            JsonNode likeData = userProfileRestService.postLike(String.valueOf(userId), content.get(0).get("userId").toString());
            if (likeData.get("isMatch").asBoolean()) {
                replies.add(replyMessagesService.getReplyMessage(message.getChatId(), likeData.get("message").toString().replace("\"", ""), BotState.SHOW_SEARCH));
            }
        }
        return replies;
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.SEARCH;
    }

    public List<PartialBotApiMethod<?>> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject + " : " + this);
    }
}
