package ru.liga.prerevolutionarytinderserver.repository;

import java.util.Optional;

public interface LikeRepository {
    /**
     * Добавление лайка пользователя
     *
     * @param userId         Идентификатор пользователя
     * @param favoriteUserId Идентификатор пользователя, которому поставили лайк
     */
    void insertLike(Long userId, Long favoriteUserId);

    /**
     * Получение идентификатора пользовтеля, которому поставили лайк, для проверки мэтча
     *
     * @param userId         Идентификатор пользователя
     * @param favoriteUserId Идентификатор пользователя, с которым нужно проверить мэтч
     * @return Идентификатор пользователя (userId), если мэтч есть
     */
    Optional<Long> getFavoriteUserId(Long userId, Long favoriteUserId);
}
