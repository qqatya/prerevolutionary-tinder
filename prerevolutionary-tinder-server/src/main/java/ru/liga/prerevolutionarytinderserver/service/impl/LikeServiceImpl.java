package ru.liga.prerevolutionarytinderserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.model.Like;
import ru.liga.prerevolutionarytinderserver.model.MatchMessage;
import ru.liga.prerevolutionarytinderserver.repository.LikeRepository;
import ru.liga.prerevolutionarytinderserver.service.LikeService;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private static final String MATCH_MESSAGE = "Вы любимы";
    private final LikeRepository likeRepository;

    @Override
    public MatchMessage putLike(Long userId, Like like) {
        likeRepository.insertLike(userId, like.getLikedUserId());
        if (likeRepository.getFavoriteUserId(userId, like.getLikedUserId()).isPresent()) {
            return new MatchMessage(true, MATCH_MESSAGE);
        }
        return new MatchMessage(false);
    }
}
