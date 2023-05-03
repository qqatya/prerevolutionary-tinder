package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.enums.Gender;
import ru.liga.prerevolutionarytinderserver.model.PageableProfile;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.SearchRepository;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;
import ru.liga.prerevolutionarytinderserver.service.SearchService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;
    private final ProfileService profileService;

    @Override
    public PageableProfile searchProfiles(Pageable pageable, Long userId) {
        Profile current = profileService.getProfile(userId);
        log.info("Searching profiles for userId = {} with search criteria = {}", current.getUserId(),
                current.getSearch().name());

        if (current.getSearch() == Gender.ALL) {
            return searchRepository.searchAllGenders(pageable, userId, current.getGender());
        } else {
            return searchRepository.searchSpecifiedGender(pageable, userId, current.getGender(),
                    current.getSearch());
        }
    }
}
