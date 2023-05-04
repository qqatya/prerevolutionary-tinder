package ru.liga.prerevolutionarytindercommon.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MatchMessageDto {
    @NonNull
    private Boolean isMatch;
    private String message;

}
