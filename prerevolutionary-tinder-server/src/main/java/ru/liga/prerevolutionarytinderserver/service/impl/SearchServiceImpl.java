package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;
import ru.liga.prerevolutionarytinderserver.model.PageableProfile;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.SearchRepository;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;
import ru.liga.prerevolutionarytinderserver.service.SearchService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;
    private final ProfileService profileService;

    @Override
    public PageableProfile searchProfiles(Pageable pageable, Long userId) {
        Profile current = profileService.getProfile(userId);
        List<Profile> totalSearchResult;

        if (current.getSearch() == GenderEnum.ALL) {
            totalSearchResult = searchRepository.searchAllGenders(pageable, userId, current.getGender());
        } else {
            totalSearchResult = searchRepository.searchSpecifiedGender(pageable, userId, current.getGender(),
                    current.getSearch());
        }
        List<Profile> currentSearchResult = new ArrayList<>();

        currentSearchResult.add(totalSearchResult.get((int) pageable.getOffset()));
        PageImpl<Profile> searchResultPage = new PageImpl<>(currentSearchResult, pageable, totalSearchResult.size());

        return new PageableProfile(searchResultPage.getContent(), searchResultPage.getTotalPages(),
                searchResultPage.getTotalElements());
    }
}
