package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.mapper.CountMapper;
import ru.liga.prerevolutionarytinderserver.mapper.FavoriteMapper;
import ru.liga.prerevolutionarytinderserver.model.Favorite;
import ru.liga.prerevolutionarytinderserver.model.PageableFavorite;
import ru.liga.prerevolutionarytinderserver.repository.FavoritesRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FavoritesRepositoryImpl implements FavoritesRepository {
    private static final String SQL_GET_FAVORITES_BY_USER_ID = "with favorites_of_user as "
            + "(select user_id as curr_user, favorite_user_id as search_result "
            + "from pretinder.user_favorite where user_id = :user_id), "
            + "user_is_favorite as (select user_id as search_result, favorite_user_id as curr_user "
            + "from pretinder.user_favorite where favorite_user_id = :user_id), "
            + "search as (select search_result, 'LIKE' as status "
            + "from favorites_of_user fa full join user_is_favorite uf using(search_result) where uf.curr_user is null "
            + "union "
            + "select search_result, 'LIKED_BY' as status "
            + "from favorites_of_user fa full join user_is_favorite uf using(search_result) where fa.curr_user is null "
            + "union "
            + "select search_result, 'MATCH' as status "
            + "from favorites_of_user fa inner join user_is_favorite uf using(search_result)) "
            + "select user_id, name, gender, search, header, description, s.status "
            + "from pretinder.profile p "
            + "inner join search s on s.search_result = p.user_id "
            + "limit :limit offset :offset";
    private static final String SQL_GET_FAVORITES_COUNT_BY_USER_ID = "with favorites_of_user as "
            + "(select user_id as curr_user, favorite_user_id as search_result "
            + "from pretinder.user_favorite where user_id = :user_id), "
            + "user_is_favorite as (select user_id as search_result, favorite_user_id as curr_user "
            + "from pretinder.user_favorite where favorite_user_id = :user_id), "
            + "search as (select search_result, 'LIKE' as status "
            + "from favorites_of_user fa full join user_is_favorite uf using(search_result) where uf.curr_user is null "
            + "union "
            + "select search_result, 'LIKED_BY' as status "
            + "from favorites_of_user fa full join user_is_favorite uf using(search_result) where fa.curr_user is null "
            + "union "
            + "select search_result, 'MATCH' as status "
            + "from favorites_of_user fa inner join user_is_favorite uf using(search_result)) "
            + "select count(user_id) "
            + "from pretinder.profile p "
            + "inner join search s on s.search_result = p.user_id ";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final FavoriteMapper favoriteMapper;
    private final CountMapper countMapper;

    @Override
    public PageableFavorite findFavoritesByUserId(Pageable pageable, Long userId) {
        var params = new MapSqlParameterSource();

        params.addValue("user_id", userId);
        params.addValue("limit", pageable.getPageSize());
        params.addValue("offset", pageable.getOffset());
        List<Favorite> favorites = jdbcTemplate.query(SQL_GET_FAVORITES_BY_USER_ID,
                params, favoriteMapper);
        Integer favoritesCount = jdbcTemplate.query(SQL_GET_FAVORITES_COUNT_BY_USER_ID, params, countMapper)
                .stream().findFirst().orElseThrow();

        log.debug("User's favorites amount = {}", favoritesCount);
        PageImpl<Favorite> favoritePage = new PageImpl<>(favorites, pageable, favoritesCount);

        log.info("Finished favorites search by userId: {}", userId);
        return new PageableFavorite(favoritePage.getContent(), favoritePage.getTotalPages(),
                favoritePage.getTotalElements());
    }
}
