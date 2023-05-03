package ru.liga.prerevolutionarytinderserver.repository;

import ru.liga.prerevolutionarytinderserver.model.State;

import java.util.Optional;

public interface UserStateRepository {
    /**
     * Создание состояния пользователя
     *
     * @param state Состояние
     * @return Созданное состояние
     */
    Optional<State> insertUserState(State state);

    /**
     * Обновление состояния пользователя
     *
     * @param userId Идентификатор пользователя
     * @param state  Состояние
     * @return Обновленное состояние
     */
    Optional<State> updateUserState(Long userId, State state);

    /**
     * Получение состояния пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Состояние
     */
    Optional<State> getUserStateByUserId(Long userId);
}
