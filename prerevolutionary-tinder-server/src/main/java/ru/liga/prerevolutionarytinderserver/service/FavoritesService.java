package ru.liga.prerevolutionarytinderserver.service;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytindercommon.dto.favorite.PageableFavoriteDto;

public interface FavoritesService {
    /**
     * Постраничный поиск любимцев пользователя (любим вами, вы любимы, взаимность)
     *
     * @param pageable Информация по номеру страницы и количеству записей в ней
     * @param userId   Идентификатор пользователя
     * @return Объект, содержащий всех любимцев и информацию по количеству страниц и любимцев
     */
    PageableFavoriteDto findFavorites(Pageable pageable, Long userId);
}
