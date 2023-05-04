package ru.liga.prerevolutionarytinderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prerevolutionarytinderserver.enums.Gender;
import ru.liga.prerevolutionarytinderserver.model.Profile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long userId;
    private String name;
    private Gender gender;
    private String header;
    private String description;
    private Gender search;

    public ProfileDto(Profile profile) {
        this.userId = profile.getUserId();
        this.name = profile.getName();
        this.gender = profile.getGender();
        this.header = profile.getHeader();
        this.description = profile.getDescription();
        this.search = profile.getSearch();
    }
}
