package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;
import ru.liga.prerevolutionarytinderserver.mapper.ProfileMapper;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.SearchRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepository {
    private static final String SQL_GET_PROFILES_OF_ALL_GENDERS = "select user_id, name, gender, search, header, " +
            "description from pretinder.profile where (search = 'ALL' or search = :user_gender) " +
            "and user_id != :user_id order by :order";
    private static final String SQL_GET_PROFILES_OF_SPECIFIED_GENDER = "select user_id, name, gender, search, header, "
            + "description from pretinder.profile where gender = :user_preference " +
            "and (search = :user_gender or SEARCH = 'ALL') and user_id != :user_id order by :order";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProfileMapper profileMapper;

    @Override
    public List<Profile> searchAllGenders(Pageable pageable, Long userId, GenderEnum userGender) {
        Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("id");
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("user_gender", String.valueOf(userGender));
        params.addValue("order", order.getDirection().name());
        return jdbcTemplate.query(SQL_GET_PROFILES_OF_ALL_GENDERS,
                params, profileMapper);
    }

    @Override
    public List<Profile> searchSpecifiedGender(Pageable pageable, Long userId, GenderEnum userGender,
                                               GenderEnum preference) {
        Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Order.by("id");
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("user_gender", String.valueOf(userGender));
        params.addValue("user_preference", String.valueOf(preference));
        params.addValue("order", order.getDirection().name());
        return jdbcTemplate.query(SQL_GET_PROFILES_OF_SPECIFIED_GENDER,
                params, profileMapper);
    }
}
