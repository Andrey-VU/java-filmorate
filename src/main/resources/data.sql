INSERT INTO genre (name) VALUES 'Комедия', 'Драма', 'Мультфильм', 'Триллер', 'Документальный', 'Боевик';
INSERT INTO rate_mpa (name) VALUES 'G', 'PG', 'PG-13 ', 'R', 'NC-17';

-- 'G' у фильма нет возрастных ограничений,
-- 'PG' детям рекомендуется смотреть фильм с родителями,
-- 'PG-13 детям до 13 лет просмотр не желателен,
-- 'R' лицам до 17 лет просматривать фильм можно только в присутствии взрослого,
-- 'NC-17 лицам до 18 лет просмотр запрещён.

--
--INSERT INTO users (name, birthday, login, email) VALUES ('name', '1979-12-25', 'login', 'e@email.ru');
--INSERT INTO films (name, description, releasedate, duration, rate_id) VALUES ('BlackAndRad', 'about Life',
--                                                                                '1900-03-25', '57', 2);
--INSERT INTO films (name, description, releasedate, duration, rate_id) VALUES ('BlackAndRad', 'about Life',
--                                                                                '1900-03-25', '57', 2);
