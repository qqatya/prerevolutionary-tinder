package ru.liga.prerevolutionarytinderserver.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.mapper.ProfileMapper;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.ProfileRepository;

import java.util.Optional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private static final String SQL_INSERT_PROFILE =
            "insert into pretinder.profile (user_id, name, gender, search, description, delete_dttm) "
                    + "values (:user_id, :name, :gender, :search, :description, :delete_dttm)";

    private static final String SQL_GET_PROFILE_BY_USER_ID =
            "select user_id, name, gender, search, description, search from pretinder.profile where user_id = :user_id";
    private static final String SQL_UPDATE_PROFILE =
            "update pretinder.profile set name = :name, gender = :gender, search = :search, description = :description"
                    + " where user_id = :user_id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, ProfileMapper profileMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.profileMapper = profileMapper;
    }

    @Override
    public void insertProfile(Profile profile) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", profile.getUserId());
        params.addValue("name", profile.getName());
        params.addValue("gender", profile.getGender().name());
        params.addValue("search", profile.getSearch().name());
        params.addValue("description", profile.getDescription());
        params.addValue("delete_dttm", null);
        jdbcTemplate.update(SQL_INSERT_PROFILE, params);
    }

    @Override
    public Optional<Profile> getProfileByUserId(Long userId) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        return jdbcTemplate.query(
                        SQL_GET_PROFILE_BY_USER_ID,
                        params,
                        profileMapper
                ).stream()
                .findFirst();
    }

    @Override
    public void updateProfile(Profile profile) {
        var params = new MapSqlParameterSource();

        params.addValue("name", profile.getName());
        params.addValue("gender", profile.getGender().name());
        params.addValue("search", profile.getSearch().name());
        params.addValue("description", profile.getDescription());
        params.addValue("user_id", profile.getUserId());
        jdbcTemplate.update(SQL_UPDATE_PROFILE, params);
    }
}
