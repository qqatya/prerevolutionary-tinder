package ru.liga.prerevolutionarytinderimagecreator.exception;

public class BackgroundNotFoundException extends RuntimeException {
    public BackgroundNotFoundException(String fileName) {
        super("Фон не найден: " + fileName);
    }
}
