package ru.liga.prerevolutionarytindertgbotclient.botApi.handlers.stage;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.prerevolutionarytindercommon.dto.favorite.FavoriteDto;
import ru.liga.prerevolutionarytindercommon.dto.favorite.PageableFavoriteDto;
import ru.liga.prerevolutionarytindercommon.dto.profile.ProfileDto;
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
    private long totalElements = 0;
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
        PageableFavoriteDto userData = userProfileRestService.searchFavorites(String.valueOf(userId), pageCount, PAGE_SIZE);
        FavoriteDto content = userData.getContent().get(0);
        ProfileDto profile = content.getProfile();
        totalElements = userData.getTotalElements();
        byte[] userProfileImage = userProfileRestService.getUserImage(profile.getUserId());
        pageCount += indexTo;
        if (totalElements <= pageCount || pageCount <= 0) {
            pageCount = 0;
        }
        String replyMessage = createReplyMessage(profile.getGender().getName(),
                content.getStatus().getStatusName(),
                profile.getName());
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

    private String createReplyMessage(String genderName, String statusName, String userName) {
        return genderName + ", " +
                userName + ", " +
                statusName + ".";
    }
}
