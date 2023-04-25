package ru.liga.prerevolutionarytindertgbotclient.utils;

import ru.liga.prerevolutionarytindertgbotclient.model.BotStage;
import ru.liga.prerevolutionarytindertgbotclient.model.BotState;

public class BotStateUtils {
    public static BotStage getBotCurrentStage(BotState currentState) {
        return switch (currentState) {
            case ASK_NAME, ASK_GENDER, ASK_DESCRIPTION, ASK_PREFERENCES, UPDATING_USER_PROFILE, USER_PROFILE_IS_READY ->
                    BotStage.UPDATING_PROFILE;
            case SHOW_USER_PROFILE -> BotStage.USER_PROFILE;
            case SHOW_MAIN_MENU -> BotStage.MAIN_MENU;
            case SHOW_FAVORITES -> BotStage.FAVORITES;
            case SHOW_SEARCH -> BotStage.SEARCH;
        };
    }
}
