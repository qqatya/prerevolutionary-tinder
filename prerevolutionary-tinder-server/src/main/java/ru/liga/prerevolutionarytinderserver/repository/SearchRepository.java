package ru.liga.prerevolutionarytinderserver.repository;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;
import ru.liga.prerevolutionarytinderserver.model.Profile;

import java.util.List;

public interface SearchRepository {
    /**
     * Постраничный поиск анкет, если критерий поиска = ALL
     *
     * @param pageable Информация по номеру страницы и количеству записей в ней
     * @param userId   Идентификатор пользователя
     * @param userGender Пол пользователя
     * @return Страница с желаемым количестаом анкет
     */
    List<Profile> searchAllGenders(Pageable pageable, Long userId, GenderEnum userGender);

    /**
     * Постраничный поиск анкет, если критерий поиска = FEMALE/MALE
     *
     * @param pageable Информация по номеру страницы и количеству записей в ней
     * @param userId Идентификатор пользователя
     * @param userGender Пол пользователя
     * @param preference Поисковой критерий пользователя
     * @return Страница с желаемым количестаом анкет
     */
    List<Profile> searchSpecifiedGender(Pageable pageable, Long userId, GenderEnum userGender, GenderEnum preference);
}
