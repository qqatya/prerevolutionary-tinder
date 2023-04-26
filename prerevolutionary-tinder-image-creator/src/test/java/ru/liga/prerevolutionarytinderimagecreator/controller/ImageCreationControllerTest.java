package ru.liga.prerevolutionarytinderimagecreator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.prerevolutionarytinderimagecreator.model.ProfileDescription;
import ru.liga.prerevolutionarytinderimagecreator.service.ImageCreationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ImageCreationController.class)
class ImageCreationControllerTest {
    @MockBean
    ImageCreationService imageCreationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void returnStatus201WhenInputIsValid() throws Exception {
        ProfileDescription profileDescription = new ProfileDescription("Выйду замуж", "но не сегодня");

        mockMvc.perform(post("/image")
                        .content(objectMapper.writeValueAsString(profileDescription))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }
}