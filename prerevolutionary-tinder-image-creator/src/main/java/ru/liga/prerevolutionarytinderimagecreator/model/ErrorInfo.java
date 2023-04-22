package ru.liga.prerevolutionarytinderimagecreator.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
    @NonNull
    private String errorMessage;
}
