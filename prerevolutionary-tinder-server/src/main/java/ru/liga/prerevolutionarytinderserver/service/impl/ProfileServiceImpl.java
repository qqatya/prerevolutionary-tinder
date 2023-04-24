package ru.liga.prerevolutionarytinderserver.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.exception.ImageNotFoundException;
import ru.liga.prerevolutionarytinderserver.exception.ProfileNotFoundException;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.ProfileRepository;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void createProfile(Profile profile) {
        parseDescription(profile);
        profileRepository.insertProfile(profile);
    }

    @Override
    public Profile getProfile(long userId) {
        return profileRepository.getProfileByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId));
    }

    @Override
    public void updateProfile(Profile profile, Long userId) {
        profileRepository.getProfileByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException(profile.getUserId()));
        if (profile.getDescription() != null) {
            parseDescription(profile);
        }
        profileRepository.updateProfile(userId, profile);
    }

    @Override
    public byte[] getPicture(Long userId) {
        InputStream in = this.getClass()
                .getResourceAsStream("/test.jpg");
        try {
            return IOUtils.toByteArray(in);
        } catch (IOException | NullPointerException e) {
            throw new ImageNotFoundException("Изображение не найдено");
        }
    }

    private void parseDescription(Profile profile) {
        String description = profile.getDescription();
        StringBuilder sb = new StringBuilder(description);
        int headerEnd;

        if (description.contains("\n")) {
            String[] split = description.split("\n");
            int firstLineIdx = 0;

            profile.setHeader(split[firstLineIdx]);
            headerEnd = profile.getHeader().length() + "\n".length();
        } else {
            String[] split = description.split("\\s+");
            int firstWordIdx = 0;

            profile.setHeader(split[firstWordIdx]);
            headerEnd = profile.getHeader().length();
        }
        profile.setDescription(sb.substring(headerEnd, description.length()).trim());
    }
}
