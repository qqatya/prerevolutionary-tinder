package ru.liga.prerevolutionarytindertgbotclient.service.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

@Service
public class UserProfileService {
    private final Environment env;
    private final RestTemplate restTemplate;

    public UserProfileService(Environment env, RestTemplateBuilder restTemplateBuilder) {
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
    public void updateUserProfile(UserProfile userProfile) {
        String url = env.getProperty("server-url") + "/profile/update/" + userProfile.getUserId();
        HttpEntity<UserProfile> entity = new HttpEntity<>(userProfile);
        try {
            this.restTemplate.exchange(url, HttpMethod.PUT, entity, UserProfile.class);
        } catch (HttpStatusCodeException ex) {
            ex.printStackTrace();
        }
    }
}
