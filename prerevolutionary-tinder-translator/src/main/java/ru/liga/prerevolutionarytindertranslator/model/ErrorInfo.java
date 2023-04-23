package ru.liga.prerevolutionarytindertranslator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
    @NonNull
    private String errorMessage;
}

