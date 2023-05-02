CREATE TABLE pretinder.user_state (
    user_id             integer                         NOT NULL,
    state               text                            NOT NULL,
    PRIMARY KEY (user_id)
);

COMMENT ON TABLE pretinder.user_state is 'Состояние пользователя';
COMMENT ON COLUMN pretinder.user_state.user_id is 'Идентификатор пользователя (первичный ключ)';
COMMENT ON COLUMN pretinder.user_state.state is 'Состояние пользователя';