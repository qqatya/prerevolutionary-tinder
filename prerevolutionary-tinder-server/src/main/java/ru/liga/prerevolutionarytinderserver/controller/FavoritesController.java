package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.repository.impl.FavoritesRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("favorites")
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesRepositoryImpl favoritesRepository;
    @GetMapping(params = { "page", "size" })
    public List<Profile> findAllByPage(@RequestParam("page") final int page, @RequestParam("size") final int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<Profile> result = favoritesRepository.findFavorites(pageable);

        if (!result.isEmpty())
            return result.getContent();
        else
            return new ArrayList<>();
    }
}
