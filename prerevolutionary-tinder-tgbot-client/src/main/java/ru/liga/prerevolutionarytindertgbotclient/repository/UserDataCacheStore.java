package ru.liga.prerevolutionarytindertgbotclient.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class UserDataCacheStore implements DataCacheStore {
    private final Map<Long, UserProfile> usersProfile = new HashMap<>();

    @Override
    public UserProfile getUserProfile(long userId) {
        UserProfile profile = usersProfile.get(userId);
        if (profile == null) {
            profile = new UserProfile();

        }
        return profile;
    }
    public void deleteUserProfile(long userId) {
        usersProfile.remove(userId);
    }

    @Override
    public void saveUserProfile(long userId, UserProfile profile) {
        usersProfile.put(userId, profile);
    }
}
