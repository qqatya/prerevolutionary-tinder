INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (1, 'Зинаида', 'FEMALE', 'ALL',
'За совсем старого дворянина, миллионера выйду замуж. Дворянка, 35 лет. Подробности в переписке. Москва, Леонтьевский пер.',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (2, 'Аркадий', 'MALE', 'ALL', 'Шатен, 30 лет. Скромный, трезвый, хорошего характера', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (3, 'Иван', 'MALE', 'ALL', 'Иван, 30 лет', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (4, 'Олег', 'MALE', 'ALL', 'Для себя обеспечен, не пью, не курю, в карты не играю. 35 лет.', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (5, 'Ольга', 'FEMALE', 'ALL', 'Желаю выйти замуж. Брюнетка, выше среднего роста, стройная, 25 лет, говорят очень недурненькая, но бедна, преданного нет.',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (6, 'Анна', 'FEMALE', 'ALL', 'Находка', null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (7, 'Игорь', 'MALE', 'ALL', 'Моя жена-подруга должна быть молода, изящна и мила, без предрассудков, эксцентрична.',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (8, 'Александр', 'MALE', 'ALL', 'На пожилой, больной или с физическим недостатком особе желает немедленно жениться, 28 л., с хорошей наружностью, но только что потерявший зрение на один глаз, образованный, имеющий прибыльное дело',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (9, 'Лилия', 'FEMALE', 'ALL', 'Только что кончившая гимназию девица желает выйти замуж за холостого или бездетного вдовца с состоянием. Возраста не стесняться',
null);
INSERT INTO pretinder.profile (user_id, name, gender, search, description, delete_dttm)
VALUES (10, 'Светлана', 'FEMALE', 'ALL', '20 лет, образованная барышня ищет мужа миллионера, непременно пожилого, во избежание неверности',
null);

INSERT INTO pretinder.favorites (user_id, favorite_user_id)
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 5),
       (2, 6),
       (4, 9),
       (9, 8),
       (9, 4);