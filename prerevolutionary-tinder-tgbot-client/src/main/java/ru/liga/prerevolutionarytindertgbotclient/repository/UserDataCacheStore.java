package ru.liga.prerevolutionarytindertgbotclient.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserDataCacheStore implements DataCacheStore {
    private final Map<Long, BotState> userStates = new HashMap<>();
    private final Map<Long, UserProfile> usersProfile = new HashMap<>();
    @Override
    public void setUserCurrentBotState(long userId, BotState botState) {
        userStates.put(userId, botState);
        log.debug("Пользователь " + userId + "переведен в состояние " + botState);
    }

    @Override
    public BotState getUserCurrentBotState(long userId) {
        BotState botState = userStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_GENDER;
        }
        return botState;
    }

    @Override
    public UserProfile getUserProfile(long userId) {
        UserProfile profile = usersProfile.get(userId);
        if (profile == null) {
            profile = new UserProfile();

        }
        return profile;
    }

    @Override
    public void saveUserProfile(long userId, UserProfile profile) {
        usersProfile.put(userId, profile);
        log.debug("Пользователь " + userId + "обновил профиль " + profile.toString());
    }
}
