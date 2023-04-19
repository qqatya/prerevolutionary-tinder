package ru.liga.prerevolutionarytinderserver.repository;

import ru.liga.prerevolutionarytinderserver.model.Profile;

import java.util.Optional;

public interface ProfileRepository {
    /**
     * Создание анкеты
     * @param profile Объект анкеты
     */
    void insertProfile(Profile profile);

    /**
     * Получение анкеты по идентификатору
     * @param userId Идентификатор пользователя
     * @return Объект анкеты
     */
    Optional<Profile> getProfileByUserId(Long userId);

    /**
     * Обновление анкеты
     * @param profile Объект анкеты
     */
    void updateProfile(Profile profile);
}
