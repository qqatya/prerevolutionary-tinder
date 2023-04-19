package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.mapper.ProfileMapper;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.FavoritesRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryImpl implements FavoritesRepository {
    private static final String SQL_GET_FAVORITES_BY_USER_ID = "select user_id, name, gender, search, description, "
            + "search "
            + "from pretinder.profile "
            + "where user_id in (select favorite_user_id from pretinder.favorites where user_id = :user_id) "
            + "order by :order ";
    private static final String SQL_GET_USERS_HAVING_USER_ID_AS_FAVORITE = "select user_id, name, gender, search, "
            + "description, search "
            + "from pretinder.profile "
            + "where user_id in (select user_id from pretinder.favorites where favorite_user_id = :favorite_user_id) "
            + "order by :order ";
    
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProfileMapper profileMapper;

    @Override
    public List<Profile> findFavoritesByUserId(Pageable page, Long userId) {
        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("id");
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("order", order.getDirection().name());
        params.addValue("limit", page.getPageSize());
        params.addValue("offset", page.getOffset());
        return jdbcTemplate.query(SQL_GET_FAVORITES_BY_USER_ID,
                params, profileMapper);
    }

    @Override
    public List<Profile> findUsersHavingUserIdAsFavorite(Pageable page, Long userId) {
        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("ID");
        var params = new MapSqlParameterSource();

        params.addValue("favorite_user_id", userId);
        params.addValue("order", order.getDirection().name());
        params.addValue("limit", page.getPageSize());
        params.addValue("offset", page.getOffset());
        return jdbcTemplate.query(SQL_GET_USERS_HAVING_USER_ID_AS_FAVORITE,
                params, profileMapper);
    }
}
