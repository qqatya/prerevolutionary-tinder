CREATE SCHEMA AUTHORIZATION pretinder;

CREATE TABLE pretinder.profile (
    id              SERIAL                          NOT NULL,
    user_id         integer                         NOT NULL,
    name            varchar(255)                    NOT NULL,
    gender          varchar(10)                     NOT NULL,
    search          varchar(10)                     NOT NULL,
    description     text,
    create_dttm     timestamptz DEFAULT now()       NOT NULL,
    delete_dttm     timestamptz,
    PRIMARY KEY (id),
    UNIQUE (user_id)
);

COMMENT ON TABLE pretinder.profile is 'Анкета пользователя';
COMMENT ON COLUMN pretinder.profile.id is 'Уникальный идентификатор (первичный ключ)';
COMMENT ON COLUMN pretinder.profile.user_id is 'Идентификатор пользователя';
COMMENT ON COLUMN pretinder.profile.name is 'Имя пользователя';
COMMENT ON COLUMN pretinder.profile.gender is 'Пол';
COMMENT ON COLUMN pretinder.profile.search is 'Категория поиска';
COMMENT ON COLUMN pretinder.profile.description is 'Описание анкеты';
COMMENT ON COLUMN pretinder.profile.create_dttm is 'Дата и время создания записи';
COMMENT ON COLUMN pretinder.profile.delete_dttm is 'Дата и время удаления записи';

CREATE TABLE pretinder.user_favorite (
    id                  SERIAL                          NOT NULL,
    user_id             integer                         NOT NULL,
    favorite_user_id    integer                         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES profile(user_id) ON DELETE CASCADE
);

COMMENT ON TABLE pretinder.user_favorite is 'Любимцы пользователей';
COMMENT ON COLUMN pretinder.user_favorite.id is 'Уникальный идентификатор (первичный ключ)';
COMMENT ON COLUMN pretinder.user_favorite.user_id is 'Идентификатор пользователя';
COMMENT ON COLUMN pretinder.user_favorite.favorite_user_id is 'Идентификатор любимца';
