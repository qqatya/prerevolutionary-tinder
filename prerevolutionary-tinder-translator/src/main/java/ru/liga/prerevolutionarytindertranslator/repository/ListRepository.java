package ru.liga.prerevolutionarytindertranslator.repository;

import java.util.Set;

public interface ListRepository {
    /**
     * Получение имен
     * @return Множество имен
     */
    Set<String> getAllNames();

    /**
     * Получение корней слов
     * @return Множество корней слов
     */
    Set<String> getAllRoots();
}
