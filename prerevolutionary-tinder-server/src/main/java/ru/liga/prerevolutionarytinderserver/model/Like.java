package ru.liga.prerevolutionarytinderserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @NonNull
    Long likedUserId;
}
