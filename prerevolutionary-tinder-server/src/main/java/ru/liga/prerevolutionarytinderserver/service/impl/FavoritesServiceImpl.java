package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.enums.FavoriteStatusEnum;
import ru.liga.prerevolutionarytinderserver.model.Favorite;
import ru.liga.prerevolutionarytinderserver.model.PageableFavorite;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.impl.FavoritesRepositoryImpl;
import ru.liga.prerevolutionarytinderserver.service.FavoritesService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesRepositoryImpl favoritesRepository;

    @Override
    public PageableFavorite findFavorites(Pageable pageable, Long userId) {
        List<Profile> favoritesOfUser = favoritesRepository.findFavoritesByUserId(pageable, userId);
        List<Profile> userIsFavorite = favoritesRepository.findUsersHavingUserIdAsFavorite(pageable, userId);
        List<Profile> matches = new ArrayList<>(favoritesOfUser);

        matches.retainAll(userIsFavorite);
        favoritesOfUser.removeAll(matches);
        userIsFavorite.removeAll(matches);
        List<Favorite> totalFavorites = new ArrayList<>();

        for (Profile profile : matches) {
            totalFavorites.add(new Favorite(profile, FavoriteStatusEnum.MATCH));
        }
        for (Profile profile : userIsFavorite) {
            totalFavorites.add(new Favorite(profile, FavoriteStatusEnum.LIKED_BY));
        }
        for (Profile profile : favoritesOfUser) {
            totalFavorites.add(new Favorite(profile, FavoriteStatusEnum.LIKE));
        }
        List<Favorite> currentFavorites = new ArrayList<>();

        currentFavorites.add(totalFavorites.get((int) pageable.getOffset()));
        PageImpl<Favorite> favoritePage = new PageImpl<>(currentFavorites, pageable, totalFavorites.size());

        return new PageableFavorite(favoritePage.getContent(), favoritePage.getTotalPages(),
                favoritePage.getTotalElements());
    }
}
