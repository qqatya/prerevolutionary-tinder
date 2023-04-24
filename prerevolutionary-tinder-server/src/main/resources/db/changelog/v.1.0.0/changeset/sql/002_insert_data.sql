INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (1, 'Зинаида', 'FEMALE', 'ALL',
'За совсѣмъ старого дворянина, миллiонера выйду замужъ.', 'Дворянка, 35 лѣтъ. Подробности въ перѣписке. Москва, Леонтьевскiй перъ.',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (2, 'Аркадiй', 'MALE', 'ALL', 'Шатѣнъ, 30 лѣтъ.', 'Скромный, трѣзвый, хорошего характера', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (3, 'Иванъ', 'MALE', 'ALL', 'Иванъ, 30 лѣт', 'Папинъ скромняга, маминъ симпатяга', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (4, 'Олегъ', 'MALE', 'ALL', 'Для', 'себя обѣспеченъ, не пью, не курю, въ карты не играю. 35 лѣтъ.', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (5, 'Ѳекла', 'FEMALE', 'ALL', 'Желаю выйти замужъ.', 'Брюнѣтка, выше срѣднѣго роста, стройная, 25 лѣтъ, говорятъ очень нѣдурненькая, но бѣдна, прѣданного нѣтъ.',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (6, 'Анна', 'FEMALE', 'ALL', 'Находка', 'имѣть мѣня своей женой', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (7, 'Игорь', 'MALE', 'ALL', 'Моя', 'жена-подруга должна быть молода, изящна и мила, безъ прѣдрассудковъ, эксцѣнтрична.',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (8, 'Алѣксандръ', 'MALE', 'ALL', 'На', 'пожилой, больной или съ физическимъ недостаткомъ особе желаетъ нѣмѣдлѣнно жениться, 28 лъ., съ хорошей наружностью, но только что потерявшiй зрѣнiе на одинъ глазъ, образованный, имеющiй прибыльное дѣло',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (9, 'Лилiя', 'FEMALE', 'ALL', 'Только что кончившая гимназiю дѣвица желаетъ выйти замужъ за холостого или бѣздѣтного вдовца съ состоянiѣмъ.', 'Возраста не стѣсняться',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, header, description, delete_dttm)
VALUES (10, 'Свѣтлана', 'FEMALE', 'ALL', '20 лѣтъ.', 'Образованная барышня ищетъ мужа миллiонера, непрѣмѣнно пожилого, во избѣжанiе невѣрности',
null);

INSERT INTO pretinder.user_favorite (user_id, favorite_user_id)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 5),
       (2, 6),
       (4, 9),
       (9, 8),
       (9, 4);
