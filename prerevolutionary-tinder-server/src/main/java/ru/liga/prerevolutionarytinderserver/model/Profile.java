package ru.liga.prerevolutionarytinderserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private Long userId;
    private String name;
    private GenderEnum gender;
    private String header;
    private String description;
    private GenderEnum search;
}
