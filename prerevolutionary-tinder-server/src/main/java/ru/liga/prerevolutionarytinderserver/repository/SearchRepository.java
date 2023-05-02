package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;
import ru.liga.prerevolutionarytinderserver.model.PageableProfile;

public interface SearchRepository {
    /**
     * Постраничный поиск анкет, если критерий поиска = ALL
     *
     * @param pageable   Информация по номеру страницы и количеству записей в ней
     * @param userId     Идентификатор пользователя
     * @param userGender Пол пользователя
     * @return Объект, содержащий всех совпадающие по критерию анкеты и информацию по количеству страниц и анкет
     */
    PageableProfile searchAllGenders(Pageable pageable, Long userId, GenderEnum userGender);

    /**
     * Постраничный поиск анкет, если критерий поиска = FEMALE/MALE
     *
     * @param pageable   Информация по номеру страницы и количеству записей в ней
     * @param userId     Идентификатор пользователя
     * @param userGender Пол пользователя
     * @param preference Поисковой критерий пользователя
     * @return Объект, содержащий всех совпадающие по критерию анкеты и информацию по количеству страниц и анкет
     */
    PageableProfile searchSpecifiedGender(Pageable pageable, Long userId, GenderEnum userGender, GenderEnum preference);
}
