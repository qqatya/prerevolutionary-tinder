package ru.liga.prerevolutionarytindercommon.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prerevolutionarytindercommon.dto.profile.ProfileDto;
import ru.liga.prerevolutionarytindercommon.enums.FavoriteStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    private ProfileDto profile;
    private FavoriteStatus status;
}
