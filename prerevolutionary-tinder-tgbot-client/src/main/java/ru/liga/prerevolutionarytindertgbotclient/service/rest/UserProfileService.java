package ru.liga.prerevolutionarytindertgbotclient.service.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytindertgbotclient.model.UserProfile;

@Service
public class UserProfileService {
    private final Environment env;
    private final RestTemplate restTemplate;

    public UserProfileService(Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.env = env;
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserProfile getUserById(long userId) {
        String url = env.getProperty("server-url") + "/profile/" + userId;
        try {
            ResponseEntity<UserProfile> response = this.restTemplate.getForEntity(url, UserProfile.class, userId);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (RuntimeException ex) {
            ex.getMessage();
        }
        return null;

    }

    public UserProfile postUserProfile(UserProfile userProfile) {
        getUserById(userProfile.getUserId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = env.getProperty("server-url") + "/profile";
        HttpEntity<UserProfile> entity = new HttpEntity<>(userProfile);
        ResponseEntity<UserProfile> response = this.restTemplate.postForEntity(url, entity, UserProfile.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
