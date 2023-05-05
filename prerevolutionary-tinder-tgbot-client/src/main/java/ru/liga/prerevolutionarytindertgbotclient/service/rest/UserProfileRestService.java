package ru.liga.prerevolutionarytindertgbotclient.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytindercommon.dto.LikeDto;
import ru.liga.prerevolutionarytindercommon.dto.MatchMessageDto;
import ru.liga.prerevolutionarytindercommon.dto.favorite.PageableFavoriteDto;
import ru.liga.prerevolutionarytindercommon.dto.profile.PageableProfileDto;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

@Service
@Slf4j
@PropertySource("classpath:application.properties")
public class UserProfileRestService {
    @Autowired
    private final RestTemplate restTemplate;
    @Value("${server-url}")
    private String SERVER_URL;
    private final String PROFILES_PATH = "/profiles";

    public UserProfileRestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserProfile getUserById(long userId) {
        String url = SERVER_URL + PROFILES_PATH + "/" + userId;
        ResponseEntity<UserProfile> response = this.restTemplate.getForEntity(url, UserProfile.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else if (response.getStatusCode() == HttpStatus.valueOf(500)) {
            return null;
        } else {
            throw new RuntimeException("Пользователя с id " + userId + " не существует");
        }

    }

    public byte[] getUserImage(long userId) {
        String url = SERVER_URL + PROFILES_PATH + "/" + userId + "/images";
        return this.restTemplate.getForObject(url, byte[].class);
    }

    public void postUserProfile(UserProfile userProfile) {
        String url = SERVER_URL + PROFILES_PATH;
        HttpEntity<UserProfile> entity = new HttpEntity<>(userProfile);
        try {
            this.restTemplate.postForEntity(url, entity, UserProfile.class);
            return;
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode() == HttpStatusCode.valueOf(500)) {
                updateUserProfile(userProfile);
                return;
            }
        }
        throw new RuntimeException("Пользователь не был создан: " + userProfile.getUserId());
    }

    private void updateUserProfile(UserProfile userProfile) {
        String url = SERVER_URL + PROFILES_PATH + "/" + userProfile.getUserId();
        HttpEntity<UserProfile> entity = new HttpEntity<>(userProfile);
        try {
            this.restTemplate.exchange(url, HttpMethod.PUT, entity, UserProfile.class);
        } catch (HttpStatusCodeException ex) {
            ex.printStackTrace();
        }
    }

    public PageableProfileDto searchProfiles(String userId, int page, int size) {
        String url = SERVER_URL + PROFILES_PATH + "/" + userId +
                "/search?page=" + page + "&size=" + size;
        try {
            ResponseEntity<PageableProfileDto> response = this.restTemplate.getForEntity(url, PageableProfileDto.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }

    public PageableFavoriteDto searchFavorites(String userId, int page, int size) {
        String url = SERVER_URL + PROFILES_PATH + "/" + userId +
                "/favorites?page=" + page + "&size=" + size;

        try {
            ResponseEntity<PageableFavoriteDto> response = this.restTemplate.getForEntity(url, PageableFavoriteDto.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }

    public MatchMessageDto postLike(String userId, String likedUserId) {
        String url = SERVER_URL + PROFILES_PATH + "/" + userId +
                "/like";
        ObjectMapper jsonMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LikeDto> entity = new HttpEntity<>(new LikeDto(Long.valueOf(likedUserId)), headers);
        try {
            ResponseEntity<MatchMessageDto> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, MatchMessageDto.class);
            log.info("Пользователь " + userId + " поставил лайк пользователю " + likedUserId);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }
}
