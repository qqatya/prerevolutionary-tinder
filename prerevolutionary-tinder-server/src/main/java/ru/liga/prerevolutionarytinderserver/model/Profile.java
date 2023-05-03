package ru.liga.prerevolutionarytinderserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prerevolutionarytinderserver.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private Long userId;
    private String name;
    private Gender gender;
    private String header;
    private String description;
    private Gender search;
}
