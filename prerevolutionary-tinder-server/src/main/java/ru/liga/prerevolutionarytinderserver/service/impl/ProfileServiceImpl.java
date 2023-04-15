package ru.liga.prerevolutionarytinderserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.exception.ProfileNotFoundException;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.ProfileRepository;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void createProfile(Profile profile) {
        profileRepository.insertProfile(profile);
    }

    @Override
    public Profile getProfile(int userId) {
        return profileRepository.getProfileByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId));
    }
}
