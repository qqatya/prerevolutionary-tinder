package ru.liga.prerevolutionarytinderimagecreator.service;

import org.docx4j.org.capaxit.imagegenerator.Margin;
import org.docx4j.org.capaxit.imagegenerator.TextImage;

import java.awt.*;
import java.util.Map;

public interface ResponsiveFontService {
    /**
     * Создание шрифта
     *
     * @param ge       Объект, хранящий доступные для приложения шрифты
     * @param filePath Путь до файла со шрифтом
     * @param isBold   Признак толщины шрифта
     */
    void registerFont(GraphicsEnvironment ge, String filePath, boolean isBold);

    /**
     * Получение адаптивного размера шрифтов
     *
     * @param textImage   Объект, содержащий информацию об изображении
     * @param margin      Объект, содержащий информацию по отступам на изображении
     * @param header      Текст заголовка
     * @param description Текст описания
     * @param bold        Жирный шрифт
     * @param plain       Обыкновыенный шрифт
     * @return Объект, содержащий информацию о размерах шрифтов
     */
    Map<Integer, Integer> getFontSize(TextImage textImage, Margin margin, String header, String description,
                                      Font bold, Font plain);
}
