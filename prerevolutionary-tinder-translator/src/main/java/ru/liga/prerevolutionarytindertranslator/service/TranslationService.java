package ru.liga.prerevolutionarytindertranslator.service;

public interface TranslationService {
    /**
     * Перевод текста на дореволюционный русский
     * @param text Строка на современном русском языке
     * @return Переведенная строка
     */
    String translate(String text);
}
