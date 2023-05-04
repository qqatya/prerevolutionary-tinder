package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytindercommon.dto.favorite.PageableFavoriteDto;

public interface FavoritesRepository {
    /**
     * Постраничный поиск любимцев пользователя
     *
     * @param pageable Информация по номеру страницы и количеству записей в ней
     * @param userId   Идентификатор пользователя
     * @return Объект, содержащий всех любимцев и информацию по количеству страниц и любимцев
     */
    PageableFavoriteDto findFavoritesByUserId(Pageable pageable, Long userId);

}
