package ru.liga.prerevolutionarytinderserver.service;

import ru.liga.prerevolutionarytinderserver.model.Profile;

public interface ProfileService {
    /**
     * Создание анкеты
     * @param profile Объект анкеты
     */
    void createProfile(Profile profile);

    /**
     * Получение анкеты по идентификатору
     * @param userId Идентификатор пользователя
     * @return Объект анкеты
     */
    Profile getProfile(long userId);

    /**
     * Обновление анкеты
     * @param profile Объект анкеты
     */
    void updateProfile(Profile profile, Long userId);

}
