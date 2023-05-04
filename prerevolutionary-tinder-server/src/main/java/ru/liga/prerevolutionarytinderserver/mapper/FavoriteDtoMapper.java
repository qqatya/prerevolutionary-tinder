package ru.liga.prerevolutionarytinderserver.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindercommon.dto.favorite.FavoriteDto;
import ru.liga.prerevolutionarytindercommon.dto.profile.ProfileDto;
import ru.liga.prerevolutionarytindercommon.enums.FavoriteStatus;
import ru.liga.prerevolutionarytindercommon.enums.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер выборки любимцев пользователя
 */
@Component
public class FavoriteDtoMapper implements RowMapper<FavoriteDto> {
    @Override
    public FavoriteDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProfileDto profileDto = new ProfileDto(
                rs.getLong("user_id"),
                rs.getString("name"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("header"),
                rs.getString("description"),
                Gender.valueOf(rs.getString("search")));
        FavoriteStatus status = FavoriteStatus.valueOf(rs.getString("status"));

        return new FavoriteDto(profileDto, status);
    }
}
