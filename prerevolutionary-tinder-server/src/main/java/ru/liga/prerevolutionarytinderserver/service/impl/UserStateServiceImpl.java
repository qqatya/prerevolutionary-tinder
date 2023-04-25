package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.exception.StateNotFoundException;
import ru.liga.prerevolutionarytinderserver.model.State;
import ru.liga.prerevolutionarytinderserver.repository.UserStateRepository;
import ru.liga.prerevolutionarytinderserver.service.UserStateService;

@Service
@RequiredArgsConstructor
public class UserStateServiceImpl implements UserStateService {
    private final UserStateRepository userStateRepository;

    @Override
    public void createUserState(State state) {
        userStateRepository.insertUserState(state);
    }

    @Override
    public void updateUserState(Long userId, State state) {
        userStateRepository.updateUserState(userId, state);
    }

    @Override
    public State getUserState(Long userId) {
        return userStateRepository.getUserStateByUserId(userId)
                .orElseThrow(StateNotFoundException::new);
    }
}
