package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.model.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository {
    void insertProfile(Profile profile);

    Optional<Profile> getProfileByUserId(int userId);
}
