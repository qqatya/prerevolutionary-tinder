package ru.liga.prerevolutionarytinderserver.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytinderserver.enums.FavoriteStatus;
import ru.liga.prerevolutionarytinderserver.enums.Gender;
import ru.liga.prerevolutionarytinderserver.model.Favorite;
import ru.liga.prerevolutionarytinderserver.model.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер выборки любимцев пользователя
 */
@Component
public class FavoriteMapper implements RowMapper<Favorite> {
    @Override
    public Favorite mapRow(ResultSet rs, int rowNum) throws SQLException {
        Profile profile = new Profile(
                rs.getLong("user_id"),
                rs.getString("name"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("header"),
                rs.getString("description"),
                Gender.valueOf(rs.getString("search")));
        FavoriteStatus status = FavoriteStatus.valueOf(rs.getString("status"));

        return new Favorite(profile, status);
    }
}
