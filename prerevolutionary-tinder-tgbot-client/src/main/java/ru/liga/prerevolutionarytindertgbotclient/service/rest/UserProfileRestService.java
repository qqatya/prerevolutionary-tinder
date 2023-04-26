package ru.liga.prerevolutionarytindertgbotclient.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

@Service
@Slf4j
public class UserProfileRestService {
    private final Environment env;
    private final RestTemplate restTemplate;

    public UserProfileRestService(Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.env = env;
        this.restTemplate = restTemplateBuilder.additionalMessageConverters(new ByteArrayHttpMessageConverter()).build();
    }

    public UserProfile getUserById(long userId) {
        String url = env.getProperty("server-url") + "/profile/" + userId;
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
        String url = env.getProperty("server-url") + "/profile/" + userId + "/image";
        return this.restTemplate.getForObject(url, byte[].class);
    }

    public void postUserProfile(UserProfile userProfile) {
        String url = env.getProperty("server-url") + "/profile";
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
        String url = env.getProperty("server-url") + "/profile/update/" + userProfile.getUserId();
        HttpEntity<UserProfile> entity = new HttpEntity<>(userProfile);
        try {
            this.restTemplate.exchange(url, HttpMethod.PUT, entity, UserProfile.class);
        } catch (HttpStatusCodeException ex) {
            ex.printStackTrace();
        }
    }

    public JsonNode searchProfiles(String userId, int page, int size) {
        String url = env.getProperty("server-url") + "/profile/" + userId +
                "/actions/search?page=" + page + "&size=" + size;
        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
            return getSimpleJsonResponseBody(response);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode searchFavorites(String userId, int page, int size) {
        String url = env.getProperty("server-url") + "/profile/" + userId +
                "/favorites?page=" + page + "&size=" + size;

        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
            return getSimpleJsonResponseBody(response);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonNode postLike(String userId, String likedUserId) {
        String url = env.getProperty("server-url") + "/profile/" + userId +
                "/actions/like";
        ObjectMapper jsonMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonMapper.createObjectNode().put("likedUserId", Long.valueOf(likedUserId)).toString(), headers);
        try {
            ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.info("Пользователь " + userId + " поставил лайк пользователю " + likedUserId);
            return getSimpleJsonResponseBody(response);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonNode getSimpleJsonResponseBody(ResponseEntity<String> responseEntity) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
