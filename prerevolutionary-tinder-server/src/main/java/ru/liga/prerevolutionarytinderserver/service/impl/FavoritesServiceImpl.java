package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.model.PageableFavorite;
import ru.liga.prerevolutionarytinderserver.repository.impl.FavoritesRepositoryImpl;
import ru.liga.prerevolutionarytinderserver.service.FavoritesService;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritesServiceImpl implements FavoritesService {
    private final FavoritesRepositoryImpl favoritesRepository;

    @Override
    public PageableFavorite findFavorites(Pageable pageable, Long userId) {
        log.info("Start favorites search by userId: {}", userId);
        return favoritesRepository.findFavoritesByUserId(pageable, userId);
    }
}
