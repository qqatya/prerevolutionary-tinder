package ru.liga.prerevolutionarytinderserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.liga.prerevolutionarytinderserver.enums.GenderEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private long userId;
    @NonNull
    private String name;
    @NonNull
    private GenderEnum gender;
    @NonNull
    private String description;
    @NonNull
    private GenderEnum search;
}
