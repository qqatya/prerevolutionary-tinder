package ru.liga.prerevolutionarytindertgbotclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MALE("Сударь"),
    FEMALE("Сударыня");

    public final String name;
}
