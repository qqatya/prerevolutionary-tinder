package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.exception.ProfileNotFoundException;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.ProfileRepository;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

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
