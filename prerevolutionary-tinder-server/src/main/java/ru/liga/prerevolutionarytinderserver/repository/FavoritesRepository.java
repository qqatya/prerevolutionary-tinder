package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytinderserver.model.Profile;

import java.util.List;

public interface FavoritesRepository {
    /**
     * Постраничный поиск любимцев пользователя
     *
     * @param page   Информация по номеру страницы и количеству записей в ней
     * @param userId Идентификатор пользователя
     * @return Страница с желаемым количестаом пользователей
     */
    List<Profile> findFavoritesByUserId(Pageable page, Long userId);

    /**
     * Постраничный поиск пользователей, у которых текущий пользователь в любимцах
     *
     * @param page   Информация по номеру страницы и количеству записей в ней
     * @param userId Идентификатор пользователя
     * @return Страница с желаемым количестаом пользователей
     */
    List<Profile> findUsersHavingUserIdAsFavorite(Pageable page, Long userId);
}
