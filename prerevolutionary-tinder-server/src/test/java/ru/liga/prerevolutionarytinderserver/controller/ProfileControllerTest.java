package ru.liga.prerevolutionarytinderserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.prerevolutionarytinderserver.enums.Gender;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.service.FavoritesService;
import ru.liga.prerevolutionarytinderserver.service.LikeService;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;
import ru.liga.prerevolutionarytinderserver.service.SearchService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerTest {
    @MockBean
    ProfileService profileService;
    @MockBean
    FavoritesService favoritesService;
    @MockBean
    SearchService searchService;
    @MockBean
    LikeService likeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void returnStatus201AndCreatesProfile() throws Exception {
        Profile profile = new Profile(12L, "Иван", Gender.MALE, "", "Описание описание",
                Gender.ALL);

        mockMvc.perform(post("/profile")
                        .content(objectMapper.writeValueAsString(profile))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }
}