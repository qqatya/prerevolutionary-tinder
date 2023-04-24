package ru.liga.prerevolutionarytinderserver.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MatchMessage {
    @NonNull
    Boolean isMatch;
    String message;

}
