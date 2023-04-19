package ru.liga.prerevolutionarytindertgbotclient.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UserProfile {
    String name;
    Gender gender;
    String description;
    List<Gender> preferences;
}
