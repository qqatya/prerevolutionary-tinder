package ru.liga.prerevolutionarytinderserver.service;

import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.model.Profile;

@Service
public interface ProfileService {
    void createProfile(Profile profile);
    Profile getProfile(int userId);
}
