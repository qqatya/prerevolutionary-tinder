package ru.liga.prerevolutionarytinderserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytindercommon.dto.StateDto;
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
     * @param stateDto Состояние
     */
    @PostMapping
    public StateDto createUserState(@RequestBody StateDto stateDto) {
        State createdState = userStateService.createUserState(new State(stateDto.getUserId(), stateDto.getState()));

        return new StateDto(createdState.getUserId(), createdState.getState());
    }

    /**
     * Обновление состояния пользователя
     *
     * @param userId Идентификатор пользователя
     */
    @PutMapping(value = "/{userId}")
    public StateDto updateUserState(@PathVariable("userId") Long userId, @RequestBody StateDto stateDto) {
        State updatedState = userStateService.updateUserState(userId,
                new State(stateDto.getUserId(), stateDto.getState()));

        return new StateDto(updatedState.getUserId(), updatedState.getState());
    }

    /**
     * Получение состояния пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Состояние
     */
    @GetMapping(value = "/{userId}")
    public StateDto getUserState(@PathVariable("userId") Long userId) {
        State state = userStateService.getUserState(userId);

        return new StateDto(state.getUserId(), state.getState());
    }
}
