insert into roles
values (1, 'Роль пользователя', 'USER'),
    (2, 'Роль админа', 'ADMIN');

--Режиссеры
drop sequence directors_sequence;
create sequence directors_sequence;
alter sequence directors_sequence owner to postgres;

truncate table films_directors;
truncate table directors cascade;
INSERT INTO directors (id, created_by, created_when, fio, description, birth_date)
VALUES (nextval('directors_sequence'), 'admin', '2022-11-15 13:46:11.797607', 'Кристофер Нолан', 'О режиссере Кристофере Нолане: ' ||
                                                                                              'Продюсер, Сценарист, Режиссер, Оператор, Монтажер, Актер, Композитор.', '1970-07-30');
INSERT INTO directors (id, created_by, created_when, fio, description, birth_date)
VALUES (nextval('directors_sequence'), 'admin', '2022-11-15 13:47:02.414728', 'Джеймс Кэмерон',
        'О режиссере Джеймсе Кэмероне: Продюсер, Сценарист, Режиссер, Актер, Монтажер, Художник, Оператор',
        '1954-08-16');
INSERT INTO directors (id, created_by, created_when, fio, description, birth_date)
VALUES (nextval('directors_sequence'), 'admin', '2022-11-15 13:48:53.363059', 'Эльдар Рязанов',
        'О режиссере Эльдаре Рязанове: Режиссер, Сценарист, Актер',
        '1927-11-18');

INSERT INTO directors (id, created_by, created_when, fio, description, birth_date)
VALUES (nextval('directors_sequence'), 'admin', '2022-11-15 13:50:12.953413', 'Дени Вильнев',
        'О режиссере Дени Вильневе: Режиссер, Сценарист, Актер, Монтажер, Продюсер, Оператор',
        '1967-10-03');
INSERT INTO directors (id, created_by, created_when, fio, description, birth_date)
VALUES (nextval('directors_sequence'), 'admin', '2022-11-15 13:51:08.314682', 'Джордж Лукас',
        'О режиссере Джордже Лукасе: Сценарист, Продюсер, Режиссер, Актер, Монтажер, Оператор',
        '1944-05-14');

--Фильмы
drop sequence films_sequence;
create sequence films_sequence;
alter sequence films_sequence owner to postgres;
truncate table films cascade;
INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:54:28.115079', 20, 1, null, '1977-05-25', '8.6', 'Звёздные войны: Эпизод 4 — Новая надежда', 'Lucasfilm Ltd.', 121);

INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:56:12.600618', 25, 1, null, '2005-05-12', '7.6', 'Звёздные войны: Эпизод 3 — Месть ситхов', 'Lucasfilm Ltd.', 140);

INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:57:05.231780', 30, 4, null, '1984-10-26', '8.1', 'Терминатор', 'Hemdale Film', 108);

INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:57:43.883671', 30, 1, null, '2009-12-10', '7.9', 'Аватар', '20th Century Fox', 162);

INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:58:12.172216', 15, 1, null, '2017-10-03', '8.0', 'Бегущий по лезвию 2049', 'Columbia Pictures', 164);

INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:58:12.172216', 18, 8, null, '2006-10-17', '8.5', 'Престиж', 'Touchstone Pictures', 125);

INSERT INTO films (id, created_by, created_when, amount, genre, online_copy_path, publish_date, imdb, title, publish, minutes_count)
VALUES (nextval('films_sequence'), 'admin', '2022-11-15 13:58:12.172216', 10, 5, null, '1974-03-18', '7.6', 'Невероятные приключения итальянцев в России', 'Киностудия «Мосфильм»', 104);

--Фильмы и режиссеры
INSERT INTO films_directors (film_id, director_id)
VALUES (1, 5);
INSERT INTO films_directors (film_id, director_id)
VALUES (2, 5);
INSERT INTO films_directors (film_id, director_id)
VALUES (3, 2);
INSERT INTO films_directors (film_id, director_id)
VALUES (4, 2);
INSERT INTO films_directors (film_id, director_id)
VALUES (5, 4);
INSERT INTO films_directors (film_id, director_id)
VALUES (6, 1);
INSERT INTO films_directors (film_id, director_id)
VALUES (7, 3);
