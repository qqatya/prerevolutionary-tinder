package ru.liga.prerevolutionarytinderserver.service;

import org.springframework.data.domain.Pageable;
import ru.liga.prerevolutionarytindercommon.dto.profile.PageableProfileDto;

public interface SearchService {
    /**
     * Постраничный поиск анкет по обоюдному поисковому критерию
     *
     * @param pageable Информация по номеру страницы и количеству записей в ней
     * @param userId   Идентификатор пользователя
     * @return Объект, содержащий всех совпадающие по критерию анкеты и информацию по количеству страниц и анкет
     */
    PageableProfileDto searchProfiles(Pageable pageable, Long userId);
}
