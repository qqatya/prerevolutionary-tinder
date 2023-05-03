package ru.liga.prerevolutionarytinderserver.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FavoriteStatus {
    LIKE("Любим вами"),
    LIKED_BY("Вы любимы"),
    MATCH("Взаимность");

    @Getter
    private final String statusName;

}
