package ru.liga.prerevolutionarytinderserver.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
    @NonNull
    private String errorMessage;
}
