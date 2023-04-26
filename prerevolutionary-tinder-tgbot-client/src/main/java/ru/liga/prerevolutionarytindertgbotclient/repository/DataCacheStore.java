package ru.liga.prerevolutionarytindertgbotclient.repository;

import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

public interface DataCacheStore {
    UserProfile getUserProfile(long userId);

    void saveUserProfile(long userId, UserProfile profile);

}
