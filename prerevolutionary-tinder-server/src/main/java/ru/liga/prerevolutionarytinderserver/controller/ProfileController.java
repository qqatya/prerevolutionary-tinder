package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.model.Profile;
import ru.liga.prerevolutionarytinderserver.service.ProfileService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfile(@RequestBody Profile profile) {
        profileService.createProfile(profile);
    }

    @GetMapping(value = "/{userId}")
    public Profile getProfile(@PathVariable("userId") int userId) {
        return profileService.getProfile(userId);
    }
}
