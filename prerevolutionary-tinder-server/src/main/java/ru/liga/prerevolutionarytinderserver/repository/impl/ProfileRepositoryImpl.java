package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.mapper.ProfileMapper;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.ProfileRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {
    private static final String SQL_INSERT_PROFILE =
            "insert into pretinder.profile (user_id, name, gender, search, header, description, delete_dttm) "
                    + "values (:user_id, :name, :gender, :search, :header, :description, :delete_dttm)";

    private static final String SQL_GET_PROFILE_BY_USER_ID =
            "select user_id, name, gender, search, header, description, search from pretinder.profile where user_id = :user_id";
    private static final String SQL_UPDATE_PROFILE =
            "update pretinder.profile set name = :name, gender = :gender, search = :search, header = :header, " +
                    "description = :description where user_id = :user_id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProfileMapper profileMapper;

    @Override
    public Optional<Profile> insertProfile(Profile profile) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", profile.getUserId());
        params.addValue("name", profile.getName());
        params.addValue("gender", profile.getGender().name());
        params.addValue("search", profile.getSearch().name());
        params.addValue("header", profile.getHeader());
        params.addValue("description", profile.getDescription());
        params.addValue("delete_dttm", null);
        jdbcTemplate.update(SQL_INSERT_PROFILE, params);

        return getProfileByUserId(profile.getUserId());
    }

    @Override
    public Optional<Profile> getProfileByUserId(Long userId) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        return jdbcTemplate.query(
                        SQL_GET_PROFILE_BY_USER_ID,
                        params,
                        profileMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Profile> updateProfile(Long userId, Profile profile) {
        var params = new MapSqlParameterSource();

        params.addValue("name", profile.getName());
        params.addValue("gender", profile.getGender().name());
        params.addValue("search", profile.getSearch().name());
        params.addValue("header", profile.getHeader());
        params.addValue("description", profile.getDescription());
        params.addValue("user_id", userId);
        jdbcTemplate.update(SQL_UPDATE_PROFILE, params);

        return getProfileByUserId(userId);
    }
}
