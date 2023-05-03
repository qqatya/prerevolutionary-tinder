package ru.liga.prerevolutionarytindertranslator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.prerevolutionarytindertranslator.model.Text;
import ru.liga.prerevolutionarytindertranslator.service.TranslationService;

@RestController
@RequestMapping("translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public Text translate(@RequestBody Text text) {
        return new Text(translationService.translate(text.getText()));
    }

}
