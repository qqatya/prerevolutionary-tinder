package ru.liga.prerevolutionarytindercommon.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prerevolutionarytindercommon.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {
    private Long userId;
    private String name;
    private Gender gender;
    private String header;
    private String description;
    private Gender search;
}
