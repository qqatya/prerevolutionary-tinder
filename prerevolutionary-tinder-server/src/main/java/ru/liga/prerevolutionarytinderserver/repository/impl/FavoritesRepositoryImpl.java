package ru.liga.prerevolutionarytinderserver.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.liga.prerevolutionarytinderserver.mapper.ProfileMapper;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.FavoritesRepository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryImpl implements FavoritesRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProfileMapper profileMapper;

    @Override
    public Page<Profile> findFavorites(Pageable page) {
        Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Order.by("ID");

        List<Profile> profiles = jdbcTemplate.query("SELECT * FROM PRETINDER.PROFILE ORDER BY " + order.getProperty() + " "
                        + order.getDirection().name() + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset(),
                profileMapper);

        return new PageImpl<>(profiles, page, 10);
    }

    private int count() {
        return Objects.requireNonNull(jdbcTemplate.queryForObject("SELECT count(*) FROM USER", Integer.class));
    }
}
