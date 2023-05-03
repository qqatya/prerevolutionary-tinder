package ru.liga.prerevolutionarytinderserver.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.prerevolutionarytinderserver.model.State;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер состояния пользователя к таблице user_state
 */
@Component
public class StateMapper implements RowMapper<State> {
    @Override
    public State mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new State(
                rs.getLong("user_id"),
                rs.getString("state"));
    }
}
