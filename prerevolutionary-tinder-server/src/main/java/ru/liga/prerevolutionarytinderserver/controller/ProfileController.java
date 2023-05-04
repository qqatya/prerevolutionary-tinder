package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytindercommon.dto.LikeDto;
import ru.liga.prerevolutionarytindercommon.dto.MatchMessageDto;
import ru.liga.prerevolutionarytindercommon.dto.favorite.PageableFavoriteDto;
import ru.liga.prerevolutionarytindercommon.dto.profile.PageableProfileDto;
import ru.liga.prerevolutionarytindercommon.dto.profile.ProfileDto;
import ru.liga.prerevolutionarytinderserver.dto.ProfileDescription;
import ru.liga.prerevolutionarytinderserver.dto.Text;
import ru.liga.prerevolutionarytinderserver.exception.ConnectionException;
import ru.liga.prerevolutionarytinderserver.model.Like;
import ru.liga.prerevolutionarytinderserver.model.MatchMessage;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.service.FavoritesService;
import ru.liga.prerevolutionarytinderserver.service.LikeService;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;
import ru.liga.prerevolutionarytinderserver.service.SearchService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("profiles")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {
    private final ProfileService profileService;
    private final FavoritesService favoritesService;
    private final SearchService searchService;
    private final LikeService likeService;
    private final RestTemplate restTemplate;
    @Value("${pretinder.image-creator.url}")
    private String imageCreatorUrl;
    @Value("${pretinder.translator.url}")
    private String translatorUrl;

    /**
     * Создание анкеты
     *
     * @param profileDto Анкета
     * @return Созданная анкета
     */
    @PostMapping
    public ProfileDto createProfile(@RequestBody ProfileDto profileDto) {
        Profile profile = new Profile();
        Text name = new Text(profileDto.getName());
        Text description = new Text(profileDto.getDescription());
        try {
            URI uri = new URI(translatorUrl);
            profile.setName(restTemplate.postForObject(uri, name, Text.class).getText());
            profile.setDescription(restTemplate.postForObject(uri, description, Text.class).getText());
            log.debug("Finished name and description translation");
        } catch (URISyntaxException e) {
            throw new ConnectionException();
        }
        profile.setUserId(profileDto.getUserId());
        profile.setGender(profileDto.getGender());
        profile.setSearch(profileDto.getSearch());
        Profile createdProfile = profileService.createProfile(profile);

        return ProfileDto.builder()
                .userId(createdProfile.getUserId())
                .name(createdProfile.getName())
                .gender(createdProfile.getGender())
                .header(createdProfile.getHeader())
                .description(createdProfile.getDescription())
                .search(createdProfile.getSearch())
                .build();
    }

    /**
     * Получение анкеты по идентификатору
     *
     * @param userId Идентификатор пользователя
     * @return Анкета
     */
    @GetMapping(value = "/{userId}")
    public ProfileDto getProfile(@PathVariable("userId") Long userId) {
        Profile profile = profileService.getProfile(userId);

        return ProfileDto.builder()
                .userId(profile.getUserId())
                .name(profile.getName())
                .gender(profile.getGender())
                .header(profile.getHeader())
                .description(profile.getDescription())
                .search(profile.getSearch())
                .build();
    }

    /**
     * Обновление анкеты
     *
     * @param profileDto Анкета
     * @return Обновленная анкета
     */
    @PutMapping(value = "/{userId}")
    public ProfileDto updateProfile(@PathVariable("userId") Long userId, @RequestBody ProfileDto profileDto) {
        Profile profile = new Profile();
        Text name = new Text(profileDto.getName());
        Text description = new Text(profileDto.getDescription());

        try {
            URI uri = new URI(translatorUrl);
            profile.setName(restTemplate.postForObject(uri, name, Text.class).getText());
            profile.setDescription(restTemplate.postForObject(uri, description, Text.class).getText());
            log.debug("Finished name and description translation");
        } catch (URISyntaxException e) {
            throw new ConnectionException();
        }
        profile.setUserId(profileDto.getUserId());
        profile.setGender(profileDto.getGender());
        profile.setSearch(profileDto.getSearch());
        Profile updatedProfile = profileService.updateProfile(profile, userId);

        return ProfileDto.builder()
                .userId(updatedProfile.getUserId())
                .name(updatedProfile.getName())
                .gender(updatedProfile.getGender())
                .header(updatedProfile.getHeader())
                .description(updatedProfile.getDescription())
                .search(updatedProfile.getSearch())
                .build();
    }

    /**
     * Получение картинки с описанием анкеты
     *
     * @param userId Идентификатор пользователя
     * @return Байтовый массив, содержащий изображение
     */
    @GetMapping(value = "/{userId}/images", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getProfilePicture(@PathVariable("userId") Long userId) {
        Profile profile = profileService.getProfile(userId);
        ProfileDescription profileDescription = new ProfileDescription(profile.getHeader(), profile.getDescription());

        try {
            URI uri = new URI(imageCreatorUrl);
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
    public PageableFavoriteDto getFavorites(@PathVariable("userId") Long userId,
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
    @GetMapping(value = "/{userId}/search")
    public PageableProfileDto searchProfiles(@PathVariable("userId") Long userId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return searchService.searchProfiles(pageable, userId);
    }

    /**
     * Добавление лайка пользователя
     *
     * @param userId  Идентификатор пользователя
     * @param likeDto Объект, содержащий идентификатор пользователя, которому поставили лайк
     * @return Объект, содержащий сообщение о наличии мэтча
     */
    //FIXME: переделать /like в эндпоинте на /favorite; класс Like переименовать в Favorite, а текущий Favorite
    // отнаследовать от Profile и переименовать на FavoriteProfile
    @PostMapping(value = "/{userId}/like")
    public MatchMessageDto putLike(@PathVariable("userId") Long userId, @RequestBody LikeDto likeDto) {
        MatchMessage matchMessage = likeService.putLike(userId, new Like(likeDto.getLikedUserId()));

        return new MatchMessageDto(matchMessage.getIsMatch(), matchMessage.getMessage());
    }

}
