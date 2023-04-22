package ru.liga.prerevolutionarytinderimagecreator.service;

public interface ImageCreationService {
    /**
     * Создание изображения с текстом анкеты
     * @param header Заголовок анкеты
     * @param description Описание анкеты
     * @return Изображение
     */
    byte[] createPicture(String header, String description);
}
