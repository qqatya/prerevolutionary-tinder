package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytinderserver.dto.ProfileDescription;
import ru.liga.prerevolutionarytinderserver.dto.Text;
import ru.liga.prerevolutionarytinderserver.exception.ConnectionException;
import ru.liga.prerevolutionarytinderserver.model.*;
import ru.liga.prerevolutionarytinderserver.service.FavoritesService;
import ru.liga.prerevolutionarytinderserver.service.LikeService;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;
import ru.liga.prerevolutionarytinderserver.service.SearchService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("profile")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private static final String IMAGE_CREATOR_URL = "pretinder.image-creator.url";
    private static final String TRANSLATOR_URL = "pretinder.translator.url";
    private final Environment env;
    private final ProfileService profileService;
    private final FavoritesService favoritesService;
    private final SearchService searchService;
    private final LikeService likeService;

    /**
     * Создание анкеты
     *
     * @param profile Объект анкеты
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfile(@RequestBody Profile profile) {
        RestTemplate restTemplate = new RestTemplate();
        Text name = new Text(profile.getName());
        Text description = new Text(profile.getDescription());
        try {
            URI uri = new URI(env.getProperty(TRANSLATOR_URL) + "/translation");
            profile.setName(restTemplate.postForObject(uri, name, Text.class).getText());
            profile.setDescription(restTemplate.postForObject(uri, description, Text.class).getText());
            log.debug("Finished name and description translation");
        } catch (URISyntaxException e) {
            throw new ConnectionException();
        }
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
        RestTemplate restTemplate = new RestTemplate();
        Text name = new Text(profile.getName());
        Text description = new Text(profile.getDescription());

        try {
            URI uri = new URI(env.getProperty(TRANSLATOR_URL) + "/translation");
            profile.setName(restTemplate.postForObject(uri, name, Text.class).getText());
            profile.setDescription(restTemplate.postForObject(uri, description, Text.class).getText());
            log.debug("Finished name and description translation");
        } catch (URISyntaxException e) {
            throw new ConnectionException();
        }
        profileService.updateProfile(profile, userId);
    }

    /**
     * Получение картинки с описанием анкеты
     *
     * @param userId Идентификатор пользователя
     * @return Байтовый массив, содержащий изображение
     */
    @GetMapping(value = "/{userId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfilePicture(@PathVariable("userId") Long userId) {
        Profile profile = profileService.getProfile(userId);
        ProfileDescription profileDescription = new ProfileDescription(profile.getHeader(), profile.getDescription());
        RestTemplate restTemplate = new RestTemplate();

        try {
            URI uri = new URI(env.getProperty(IMAGE_CREATOR_URL) + "/image");
            log.info("Receiving image from image-creator by userId: {}", userId);
            return restTemplate.postForObject(uri, profileDescription, byte[].class);
        } catch (URISyntaxException e) {
            throw new ConnectionException();
        }
    }

    /**
     * Получение любимцев пользователя
     *
     * @param userId Идентификатор пользователя
     * @param page   Страница
     * @param size   Количество записей на странице
     * @return Страница с любимцами пользователя
     */
    @GetMapping(value = "/{userId}/favorites")
    public PageableFavorite getFavorites(@PathVariable("userId") Long userId,
                                         @RequestParam("page") int page,
                                         @RequestParam("size") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return favoritesService.findFavorites(pageable, userId);
    }

    /**
     * Поиск анкет
     *
     * @param userId Идентификатор пользователя
     * @param page   Страница
     * @param size   Количество записей на странице
     * @return Страница с результатом поиска
     */
    @GetMapping(value = "/{userId}/actions/search")
    public PageableProfile searchProfiles(@PathVariable("userId") Long userId,
                                          @RequestParam("page") int page,
                                          @RequestParam("size") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return searchService.searchProfiles(pageable, userId);
    }

    /**
     * Добавление лайка пользователя
     *
     * @param userId Идентификатор пользователя
     * @param like   Объект, содержащий идентификатор пользователя, которому поставили лайк
     * @return Объект, содержащий сообщение о наличии мэтча
     */
    @PostMapping(value = "/{userId}/actions/like")
    public MatchMessage putLike(@PathVariable("userId") Long userId, @RequestBody Like like) {
        return likeService.putLike(userId, like);
    }

}
