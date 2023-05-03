package ru.liga.prerevolutionarytinderserver.service;

import ru.liga.prerevolutionarytinderserver.model.Profile;

public interface ProfileService {
    /**
     * Создание анкеты
     *
     * @param profile Объект анкеты
     * @return Созданная анкета
     */
    Profile createProfile(Profile profile);

    /**
     * Получение анкеты по идентификатору
     *
     * @param userId Идентификатор пользователя
     * @return Объект анкеты
     */
    Profile getProfile(Long userId);

    /**
     * Обновление анкеты
     *
     * @param profile Объект анкеты
     * @return Обновленная анкета
     */
    Profile updateProfile(Profile profile, Long userId);

}
