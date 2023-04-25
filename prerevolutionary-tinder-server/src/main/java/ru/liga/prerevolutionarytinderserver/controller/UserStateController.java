package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.model.State;
import ru.liga.prerevolutionarytinderserver.service.UserStateService;

@RestController
@RequestMapping("state")
@RequiredArgsConstructor
public class UserStateController {
    private final UserStateService userStateService;

    /**
     * Создание состояния пользователя
     *
     * @param state Состояние
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserState(@RequestBody State state) {
        userStateService.createUserState(state);
    }

    /**
     * Обновление состояния пользователя
     *
     * @param userId Идентификатор пользователя
     */
    @PutMapping(value = "/update/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserState(@PathVariable("userId") Long userId, @RequestBody State state) {
        userStateService.updateUserState(userId, state);
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
