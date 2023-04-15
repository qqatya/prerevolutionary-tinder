package ru.liga.prerevolutionarytinderserver.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfileNotFoundException extends RuntimeException {
    private final int userId;

    @Override
    public String getMessage() {
        return "Profile with id = " + userId + " does not exist";
    }
}
