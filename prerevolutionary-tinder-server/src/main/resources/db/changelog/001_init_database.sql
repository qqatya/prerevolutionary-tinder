CREATE SCHEMA AUTHORIZATION pretinder;

CREATE TABLE pretinder.profile (
    id              SERIAL                          NOT NULL,
    user_id         integer                         NOT NULL,
    name            varchar(255)                    NOT NULL,
    gender          varchar(10)                     NOT NULL,
    search          varchar(10)                     NOT NULL,
    description     text,
    create_dttm     timestamptz DEFAULT now()       NOT NULL,
    modify_dttm     timestamptz DEFAULT now()       NOT NULL,
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
COMMENT ON COLUMN pretinder.profile.modify_dttm is 'Дата и время изменения записи';
COMMENT ON COLUMN pretinder.profile.delete_dttm is 'Дата и время удаления записи';

CREATE TABLE pretinder.favorites (
    id                  SERIAL                          NOT NULL,
    user_id             integer                         NOT NULL,
    favorite_user_id    integer                         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES profile(user_id) ON DELETE CASCADE
);

COMMENT ON TABLE pretinder.favorites is 'Любимцы пользователей';
COMMENT ON COLUMN pretinder.favorites.id is 'Уникальный идентификатор (первичный ключ)';
COMMENT ON COLUMN pretinder.favorites.user_id is 'Идентификатор пользователя';
COMMENT ON COLUMN pretinder.favorites.favorite_user_id is 'Идентификатор любимца';

CREATE TABLE pretinder.pictures (
    id                  SERIAL                          NOT NULL,
    user_id             integer                         NOT NULL,
    picture             text                            NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, picture),
    FOREIGN KEY (user_id) REFERENCES profile(user_id) ON DELETE CASCADE
);

COMMENT ON TABLE pretinder.pictures is 'Изображения анкет';
COMMENT ON COLUMN pretinder.pictures.id is 'Уникальный идентификатор (первичный ключ)';
COMMENT ON COLUMN pretinder.pictures.user_id is 'Идентификатор пользователя';
COMMENT ON COLUMN pretinder.pictures.picture is 'Изображение';