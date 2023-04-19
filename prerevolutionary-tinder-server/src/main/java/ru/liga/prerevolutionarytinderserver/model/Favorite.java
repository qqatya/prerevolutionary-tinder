package ru.liga.prerevolutionarytinderserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.liga.prerevolutionarytinderserver.enums.FavoriteStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Profile profile;
    private FavoriteStatusEnum status;
}
