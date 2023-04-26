package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.exception.StateNotFoundException;
import ru.liga.prerevolutionarytinderserver.model.State;
import ru.liga.prerevolutionarytinderserver.repository.UserStateRepository;
import ru.liga.prerevolutionarytinderserver.service.UserStateService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStateServiceImpl implements UserStateService {
    private final UserStateRepository userStateRepository;

    @Override
    public void createUserState(State state) {
        userStateRepository.insertUserState(state);
        log.info("Created new state for userId: {}", state.getUserId());
        log.debug("Current user state: {}", state.getState());
    }

    @Override
    public void updateUserState(Long userId, State state) {
        userStateRepository.updateUserState(userId, state);
        log.info("Updated user state for userId: {}", userId);
        log.debug("Current user state: {}", state.getState());
    }

    @Override
    public State getUserState(Long userId) {
        log.info("Getting user state for userId: {}", userId);
        return userStateRepository.getUserStateByUserId(userId)
                .orElseThrow(StateNotFoundException::new);
    }
}
