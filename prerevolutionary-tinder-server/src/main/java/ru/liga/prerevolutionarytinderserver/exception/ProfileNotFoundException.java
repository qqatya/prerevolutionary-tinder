package ru.liga.prerevolutionarytinderserver.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProfileNotFoundException extends RuntimeException {
    private final long userId;

    @Override
    public String getMessage() {
        return "Профиль с id = " + userId + " не существует";
    }
}
