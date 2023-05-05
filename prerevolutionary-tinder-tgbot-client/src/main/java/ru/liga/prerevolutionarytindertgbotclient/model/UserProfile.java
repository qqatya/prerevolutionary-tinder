package ru.liga.prerevolutionarytindertgbotclient.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.liga.prerevolutionarytindercommon.enums.Gender;

import java.io.Serializable;

@Data
@Getter
@Setter
public class UserProfile implements Serializable {
    private Long userId;
    private String name;
    private Gender gender;
    private String description;
    private Gender search;
}
