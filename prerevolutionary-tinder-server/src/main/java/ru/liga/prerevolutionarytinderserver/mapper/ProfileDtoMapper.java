package ru.liga.prerevolutionarytinderserver.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytindercommon.dto.profile.ProfileDto;
import ru.liga.prerevolutionarytindercommon.enums.Gender;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер DTO анкеты
 */
@Component
public class ProfileDtoMapper implements RowMapper<ProfileDto> {
    @Override
    public ProfileDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProfileDto(
                rs.getLong("user_id"),
                rs.getString("name"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("header"),
                rs.getString("description"),
                Gender.valueOf(rs.getString("search")));
    }
}
