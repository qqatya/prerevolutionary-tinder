package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;
import ru.liga.prerevolutionarytinderserver.mapper.CountMapper;
import ru.liga.prerevolutionarytinderserver.mapper.ProfileMapper;
import ru.liga.prerevolutionarytinderserver.model.PageableProfile;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.SearchRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SearchRepositoryImpl implements SearchRepository {
    private static final String SQL_GET_PROFILES_OF_ALL_GENDERS = "select user_id, name, gender, search, header, " +
            "description from pretinder.profile where (search = 'ALL' or search = :user_gender) " +
            "and user_id != :user_id limit :limit offset :offset";
    private static final String SQL_GET_PROFILES_COUNT_OF_ALL_GENDERS = "select count(user_id) "
            + "from pretinder.profile where (search = 'ALL' or search = :user_gender) and user_id != :user_id";
    private static final String SQL_GET_PROFILES_OF_SPECIFIED_GENDER = "select user_id, name, gender, search, header, "
            + "description from pretinder.profile where gender = :user_preference " +
            "and (search = :user_gender or SEARCH = 'ALL') and user_id != :user_id limit :limit offset :offset";
    private static final String SQL_GET_PROFILES_COUNT_OF_SPECIFIED_GENDER = "select count(user_id) "
            + "from pretinder.profile where gender = :user_preference "
            + "and (search = :user_gender or SEARCH = 'ALL') and user_id != :user_id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProfileMapper profileMapper;
    private final CountMapper countMapper;


    @Override
    public PageableProfile searchAllGenders(Pageable pageable, Long userId, GenderEnum userGender) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("user_gender", String.valueOf(userGender));
        params.addValue("limit", pageable.getPageSize());
        params.addValue("offset", pageable.getOffset());
        List<Profile> searchResult = jdbcTemplate.query(SQL_GET_PROFILES_OF_ALL_GENDERS,
                params, profileMapper);
        Integer totalSearchCount = jdbcTemplate.query(SQL_GET_PROFILES_COUNT_OF_ALL_GENDERS, params, countMapper)
                .stream().findFirst().orElseThrow();

        log.debug("Found {} search results", totalSearchCount);
        PageImpl<Profile> searchResultPage = new PageImpl<>(searchResult, pageable, totalSearchCount);

        log.info("Loading search result for page = {}", searchResultPage.getPageable().getPageNumber());
        return new PageableProfile(searchResultPage.getContent(), searchResultPage.getTotalPages(),
                searchResultPage.getTotalElements());
    }

    @Override
    public PageableProfile searchSpecifiedGender(Pageable pageable, Long userId, GenderEnum userGender,
                                                 GenderEnum preference) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("user_gender", String.valueOf(userGender));
        params.addValue("user_preference", String.valueOf(preference));
        params.addValue("limit", pageable.getPageSize());
        params.addValue("offset", pageable.getOffset());
        List<Profile> searchResult = jdbcTemplate.query(SQL_GET_PROFILES_OF_SPECIFIED_GENDER,
                params, profileMapper);
        Integer totalSearchCount = jdbcTemplate.query(SQL_GET_PROFILES_COUNT_OF_SPECIFIED_GENDER, params, countMapper)
                .stream().findFirst().orElseThrow();

        log.debug("Found {} search results", totalSearchCount);
        PageImpl<Profile> searchResultPage = new PageImpl<>(searchResult, pageable, totalSearchCount);

        log.info("Loading search result for page = {}", searchResultPage.getPageable().getPageNumber());
        return new PageableProfile(searchResultPage.getContent(), searchResultPage.getTotalPages(),
                searchResultPage.getTotalElements());

    }
}
