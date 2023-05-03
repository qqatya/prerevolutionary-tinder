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
    public State createUserState(State state) {
        log.info("Creating new state for userId: {}", state.getUserId());
        return userStateRepository.insertUserState(state)
                .orElseThrow(StateNotFoundException::new);
    }

    @Override
    public State updateUserState(Long userId, State state) {
        log.info("Updating user state for userId: {}", userId);
        return userStateRepository.updateUserState(userId, state)
                .orElseThrow(StateNotFoundException::new);
    }

    @Override
    public State getUserState(Long userId) {
        log.info("Getting user state for userId: {}", userId);
        return userStateRepository.getUserStateByUserId(userId)
                .orElseThrow(StateNotFoundException::new);
    }
}
