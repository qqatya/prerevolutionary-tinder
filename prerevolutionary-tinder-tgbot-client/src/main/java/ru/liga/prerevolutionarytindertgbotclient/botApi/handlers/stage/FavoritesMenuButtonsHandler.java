package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import com.fasterxml.jackson.databind.JsonNode;
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
public class FavoritesMenuButtonsHandler implements StageHandler {
    private final int PAGE_SIZE = 1;
    private int pageCount = 0;
    private int totalElements = 0;
    private final UserStateRestService userStateRestService;
    private final ReplyMessagesService replyMessagesService;
    private final UserProfileRestService userProfileRestService;

    public FavoritesMenuButtonsHandler(UserStateRestService userStateRestService, ReplyMessagesService replyMessagesService, UserProfileRestService userProfileRestService) {
        this.userStateRestService = userStateRestService;
        this.replyMessagesService = replyMessagesService;
        this.userProfileRestService = userProfileRestService;
    }

    public List<PartialBotApiMethod<?>> handleMessage(Message message) {
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        if (messageText.equals("Влево")) {
            return handleSwipe(message, -1);

        }
        if (messageText.equals("Вправо")) {
            return handleSwipe(message, 1);
        }
        if (messageText.equals("Меню")) {
            userStateRestService.updateUserState(String.valueOf(userId), BotState.SHOW_MAIN_MENU);
            return List.of(replyMessagesService.getReplyMessage(message.getChatId(), "Главное меню", BotState.SHOW_MAIN_MENU));
        }
        if (messageText.equals("Любимцы")) {
            return handleSwipe(message, 1);
        }
        throw new RuntimeException("Странное сообщение от бота в главном меню...");
    }

    private List<PartialBotApiMethod<?>> handleSwipe(Message message, int indexTo) {
        long userId = message.getFrom().getId();
        JsonNode userData = userProfileRestService.searchFavorites(String.valueOf(userId), pageCount, PAGE_SIZE);
        JsonNode content = userData.get("content");
        JsonNode profile = content.get(0).get("profile");
        totalElements = userData.get("totalElements").asInt();
        byte[] userProfileImage = userProfileRestService.getUserImage(profile.get("userId").asLong());
        pageCount += indexTo;
        if (totalElements <= pageCount || pageCount <= 0) {
            pageCount = 0;
        }
        String replyMessage = createReplyMessage(profile.get("gender").toString().replace("\"", ""),
                content.get(0).get("status").toString().replace("\"", ""),
                profile.get("name").toString().replace("\"", ""));
        List<PartialBotApiMethod<?>> replies = new ArrayList<>();
        replies.add(replyMessagesService.getPhotoMessage(message.getChatId(), userProfileImage, BotState.SHOW_SEARCH));
        replies.add(replyMessagesService.getReplyMessage(message.getChatId(), replyMessage, BotState.SHOW_SEARCH));
        return replies;
    }

    @Override
    public BotStage getHandlerName() {
        return BotStage.FAVORITES;
    }

    public List<PartialBotApiMethod<?>> handle(BotApiObject apiObject, BotState botState) {
        if (apiObject instanceof Message) {
            return handleMessage((Message) apiObject);
        }
        throw new RuntimeException("Данный обработчик не отвечает за обьект " + apiObject + " : " + this);
    }

    private String createReplyMessage(String gender, String status, String name) {
        System.out.println(gender);
        System.out.println(status);
        System.out.println(name);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(gender.equals("MALE"));
        System.out.println(gender.equals("FEMALE"));
        if (gender.equals("MALE")) {
            stringBuilder.append("Сударь, ");
        }
        if (gender.equals("FEMALE")) {
            stringBuilder.append("Сударыня, ");
        }
        stringBuilder.append(name).append(", ");
        if (status.equals("LIKED_BY")) {
            stringBuilder.append("Вы любимы.");
        }
        if (status.equals("MATCH")) {
            stringBuilder.append("Взаимность.");
        }
        if (status.equals("LIKE")) {
            stringBuilder.append("Любим(а) вами.");
        }
        return stringBuilder.toString().replace("\"", "");
    }
}
