package ru.liga.prerevolutionarytinderserver.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;
import ru.liga.prerevolutionarytinderserver.model.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProfileMapper implements RowMapper<Profile> {
    @Override
    public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Profile(
                rs.getInt("user_id"),
                rs.getString("name"),
                GenderEnum.valueOf(rs.getString("gender")),
                rs.getString("description"),
                GenderEnum.valueOf(rs.getString("search")));
    }
}
