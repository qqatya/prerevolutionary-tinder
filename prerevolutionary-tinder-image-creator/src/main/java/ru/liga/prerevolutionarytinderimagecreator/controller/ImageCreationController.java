package ru.liga.prerevolutionarytinderimagecreator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderimagecreator.model.ProfileDescription;
import ru.liga.prerevolutionarytinderimagecreator.service.ImageCreationService;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageCreationController {
    private final ImageCreationService imageCreationService;

    @PostMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public byte[] createImage(@RequestBody ProfileDescription profileDescription) {
        return imageCreationService.createPicture(profileDescription.getHeader(), profileDescription.getDescription());
    }
}
