package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.mapper.StateMapper;
import ru.liga.prerevolutionarytinderserver.model.State;
import ru.liga.prerevolutionarytinderserver.repository.UserStateRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserStateRepositoryImpl implements UserStateRepository {
    private static final String SQL_INSERT_STATE = "insert into pretinder.user_state (user_id, state) "
            + "values (:user_id, :state)";
    private static final String SQL_UPDATE_STATE =
            "update pretinder.user_state set user_id = :user_id, state = :state where user_id = :user_id";
    private static final String SQL_GET_STATE =
            "select user_id, state from pretinder.user_state where user_id = :user_id";
    public final StateMapper stateMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insertUserState(State state) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", state.getUserId());
        params.addValue("state", state.getState());
        jdbcTemplate.update(SQL_INSERT_STATE, params);
    }

    @Override
    public void updateUserState(Long userId, State state) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("state", state.getState());
        jdbcTemplate.update(SQL_UPDATE_STATE, params);
    }

    @Override
    public Optional<State> getUserStateByUserId(Long userId) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        return jdbcTemplate.query(
                        SQL_GET_STATE,
                        params,
                        stateMapper
                ).stream()
                .findFirst();
    }
}
