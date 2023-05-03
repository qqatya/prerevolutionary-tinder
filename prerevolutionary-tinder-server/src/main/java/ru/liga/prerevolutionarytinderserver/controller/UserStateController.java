package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.model.State;
import ru.liga.prerevolutionarytinderserver.service.UserStateService;

@RestController
@RequestMapping("states")
@RequiredArgsConstructor
public class UserStateController {
    private final UserStateService userStateService;

    /**
     * Создание состояния пользователя
     *
     * @param state Состояние
     */
    @PostMapping
    public State createUserState(@RequestBody State state) {
        return userStateService.createUserState(state);
    }

    /**
     * Обновление состояния пользователя
     *
     * @param userId Идентификатор пользователя
     */
    @PutMapping(value = "/{userId}")
    public State updateUserState(@PathVariable("userId") Long userId, @RequestBody State state) {
        return userStateService.updateUserState(userId, state);
    }

    /**
     * Получение состояния пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Состояние
     */
    @GetMapping(value = "/{userId}")
    public State getUserState(@PathVariable("userId") Long userId) {
        return userStateService.getUserState(userId);
    }
}
