package ru.liga.prerevolutionarytinderserver.service;

import ru.liga.prerevolutionarytinderserver.model.Like;
import ru.liga.prerevolutionarytinderserver.model.MatchMessage;

public interface LikeService {
    /**
     * Добавление лайка пользователя
     * @param userId Идентификатор пользователя
     * @param like Объект, содержащий идентификатор пользователя, которому поставили лайк
     * @return Объект, содержащий сообщение о наличии мэтча
     */
    MatchMessage putLike(Long userId, Like like);
}
