package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytindercommon.dto.profile.PageableProfileDto;
import ru.liga.prerevolutionarytindercommon.enums.Gender;

public interface SearchRepository {
    /**
     * Постраничный поиск анкет, если критерий поиска = ALL
     *
     * @param pageable   Информация по номеру страницы и количеству записей в ней
     * @param userId     Идентификатор пользователя
     * @param userGender Пол пользователя
     * @return Объект, содержащий всех совпадающие по критерию анкеты и информацию по количеству страниц и анкет
     */
    PageableProfileDto searchAllGenders(Pageable pageable, Long userId, Gender userGender);

    /**
     * Постраничный поиск анкет, если критерий поиска = FEMALE/MALE
     *
     * @param pageable   Информация по номеру страницы и количеству записей в ней
     * @param userId     Идентификатор пользователя
     * @param userGender Пол пользователя
     * @param preference Поисковой критерий пользователя
     * @return Объект, содержащий всех совпадающие по критерию анкеты и информацию по количеству страниц и анкет
     */
    PageableProfileDto searchSpecifiedGender(Pageable pageable, Long userId, Gender userGender, Gender preference);
}
