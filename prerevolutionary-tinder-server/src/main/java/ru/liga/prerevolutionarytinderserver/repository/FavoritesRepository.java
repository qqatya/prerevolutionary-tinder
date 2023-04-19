package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytinderserver.model.Profile;

public interface FavoritesRepository {
    Page<Profile> findFavorites(Pageable page);
}
