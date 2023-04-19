package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prerevolutionarytinderserver.model.PageableFavorite;
import ru.liga.prerevolutionarytinderserver.service.impl.FavoritesServiceImpl;

@RestController
@RequestMapping("favorites")
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesServiceImpl favoritesService;

    @GetMapping(params = {"userId", "page", "size"})
    public PageableFavorite findAllByPage(@RequestParam("userId") final Long userId,
                                          @RequestParam("page") final int page,
                                          @RequestParam("size") final int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return favoritesService.findFavorites(pageable, userId);
    }
}
