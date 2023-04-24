package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.repository.LikeRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {
    private static final String SQL_INSERT_USER_FAVORITE = "insert into pretinder.user_favorite " +
            "(user_id, favorite_user_id) values (:user_id, :favorite_user_id)";
    private static final String SQL_GET_FAVORITE_USER_ID = "select favorite_user_id from pretinder.user_favorite " +
            "where favorite_user_id = :user_id and user_id = :favorite_user_id";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LongMapper longMapper;

    @Override
    public void insertLike(Long userId, Long favoriteUserId) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("favorite_user_id", favoriteUserId);
        jdbcTemplate.update(SQL_INSERT_USER_FAVORITE, params);
    }

    @Override
    public Optional<Long> getFavoriteUserId(Long userId, Long favoriteUserId) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("favorite_user_id", favoriteUserId);
        return jdbcTemplate.query(
                        SQL_GET_FAVORITE_USER_ID,
                        params,
                        longMapper
                ).stream()
                .findFirst();
    }

    @Component
    static class LongMapper implements RowMapper<Long> {

        @Override
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong("favorite_user_id");
        }
    }
}
