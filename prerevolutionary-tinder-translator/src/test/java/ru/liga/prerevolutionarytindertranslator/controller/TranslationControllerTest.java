package ru.liga.prerevolutionarytindertranslator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.prerevolutionarytindertranslator.model.Text;
import ru.liga.prerevolutionarytindertranslator.service.TranslationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TranslationController.class)
class TranslationControllerTest {
    @MockBean
    TranslationService imageCreationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void returnStatus201WhenInputIsValid() throws Exception {
        Text text = new Text("Описание профиля");

        mockMvc.perform(post("/translation")
                        .content(objectMapper.writeValueAsString(text))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}