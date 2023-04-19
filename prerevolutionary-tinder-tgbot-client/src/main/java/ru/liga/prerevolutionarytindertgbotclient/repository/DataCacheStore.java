package ru.liga.prerevolutionarytindertgbotclient.repository;

import ru.liga.prerevolutionarytindertgbotclient.model.BotState;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

public interface DataCacheStore {
    void setUserCurrentBotState(long userId, BotState botState);
    BotState getUserCurrentBotState(long userId);
    UserProfile getUserProfile(long userId);
    void saveUserProfile(long userId, UserProfile profile);

}
