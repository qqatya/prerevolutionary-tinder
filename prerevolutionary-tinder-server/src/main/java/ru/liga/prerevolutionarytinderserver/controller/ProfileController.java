package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.model.PageableFavorite;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.service.FavoritesService;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final FavoritesService favoritesService;

    /**
     * Создание анкеты
     *
     * @param profile Объект анкеты
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfile(@RequestBody Profile profile) {
        profileService.createProfile(profile);
    }

    /**
     * Получение анкеты по идентификатору
     *
     * @param userId Идентификатор пользователя
     * @return Объект анкеты
     */
    @GetMapping(value = "/{userId}")
    public Profile getProfile(@PathVariable("userId") Long userId) {
        return profileService.getProfile(userId);
    }

    /**
     * Обновление анкеты
     *
     * @param profile Объект анкеты
     */
    @PutMapping(value = "/update/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@PathVariable("userId") Long userId, @RequestBody Profile profile) {
        profileService.updateProfile(profile, userId);
    }

    /**
     * Получение картинки с описанием анкеты
     *
     * @param userId Идентификатор пользователя
     * @return Байтовый массив, содержащий изображение
     */
    @GetMapping(value = "/{userId}/image",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfilePicture(@PathVariable("userId") Long userId) {
        return profileService.getPicture(userId);
    }

    /**
     * Получение любимцев пользователя
     * @param userId Идентификатор пользователя
     * @param page Страница
     * @param size Количество записей на странице
     * @return Страница с любимцами пользователя
     */
    @GetMapping(value = "/{userId}/favorites")
    public PageableFavorite findAllByPage(@PathVariable("userId") final Long userId,
                                          @RequestParam("page") final int page,
                                          @RequestParam("size") final int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return favoritesService.findFavorites(pageable, userId);
    }
}
