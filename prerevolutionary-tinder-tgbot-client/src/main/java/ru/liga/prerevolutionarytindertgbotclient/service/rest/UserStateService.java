package ru.liga.prerevolutionarytindertgbotclient.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

@Service
public class UserStateService {
    private final Environment env;
    private final RestTemplate restTemplate;

    public UserStateService(Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.env = env;
        this.restTemplate = restTemplateBuilder.build();
    }

    public BotState getUserState(String userId) {
        String url = env.getProperty("server-url") + "/state/" + userId;
        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
            ObjectMapper jsonMapper = new ObjectMapper();
            jsonMapper.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature());
            JsonNode node = jsonMapper.readValue(response.getBody(), ObjectNode.class);
            return BotState.valueOf(node.get("state").toString().replace("\"", ""));
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode() == HttpStatusCode.valueOf(500)) {
                return null;
            }
            throw new RuntimeException("Что то с получением стейта " + userId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void postUserState(String userId, BotState botState) {
        String url = env.getProperty("server-url") + "/state";
        HttpEntity<State> entity = new HttpEntity<>(new State(userId, botState.toString()));
        ResponseEntity<Object> response = this.restTemplate.postForEntity(url, entity, Object.class);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Что то пошло не так c добавлением стейта");
        }
    }

    public void updateUserState(String userId, BotState botState) {
        String url = env.getProperty("server-url") + "/state/update/" + userId;
        HttpEntity<State> entity = new HttpEntity<>(new State(userId, botState.toString()));
        ResponseEntity<State> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, State.class);
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new RuntimeException("Что то пошло не так c обновлением стейта");
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    private class State {
        String userId;
        String state;
    }
}
