package ru.liga.prerevolutionarytinderserver.service;

import ru.liga.prerevolutionarytinderserver.model.State;

public interface UserStateService {
    /**
     * Создание состояния пользователя
     *
     * @param state Состояние
     */
    void createUserState(State state);

    /**
     * Обновление состояния пользователя
     *
     * @param userId Идентификатор пользователя
     * @param state  Состояние
     */
    void updateUserState(Long userId, State state);

    /**
     * Получение состояния пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Состояние
     */
    State getUserState(Long userId);
}
