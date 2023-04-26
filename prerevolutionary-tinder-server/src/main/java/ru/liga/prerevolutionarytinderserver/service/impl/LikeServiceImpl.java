package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.model.Like;
import ru.liga.prerevolutionarytinderserver.model.MatchMessage;
import ru.liga.prerevolutionarytinderserver.repository.LikeRepository;
import ru.liga.prerevolutionarytinderserver.service.LikeService;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {
    private static final String MATCH_MESSAGE = "Вы любимы";
    private final LikeRepository likeRepository;

    @Override
    public MatchMessage putLike(Long userId, Like like) {
        log.debug("Processing like for userId = {} from userId = {}", like.getLikedUserId(), userId);
        likeRepository.insertLike(userId, like.getLikedUserId());
        if (likeRepository.getFavoriteUserId(userId, like.getLikedUserId()).isPresent()) {
            log.info("Sending message for match = true");
            return new MatchMessage(true, MATCH_MESSAGE);
        }
        log.info("Sending message for match = false");
        return new MatchMessage(false);
    }
}
