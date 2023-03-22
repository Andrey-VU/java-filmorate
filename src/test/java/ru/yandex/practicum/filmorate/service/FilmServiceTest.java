package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    FilmService filmService;
    Film film1;
    Film film2;
    Film film3;
    Film film4;
    Film film5;
    Film film6;
    Film film7;
    Film film8;
    Film film9;
    Film film10;
    Film film11;
    Film film12;

    @BeforeEach
    public void beforeEach() {
        filmService = new FilmService(new InMemoryFilmStorage());
        User user1 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user2 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user3 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user4 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user5 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user6 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        user1.setId(1);
        user2.setId(2);
        user3.setId(3);
        user4.setId(4);
        user5.setId(5);
        user6.setId(6);
        film1 = new Film(0, "A", "About", "1950-03-25", 1);
        film2 = new Film(0, "B", "About", "1950-03-25", 2);
        film3 = new Film(0, "C", "About", "1950-03-25", 3);
        film4 = new Film(0, "D", "About", "1950-03-25", 4);
        film5 = new Film(0, "E", "About", "1950-03-25", 5);
        film6 = new Film(0, "F", "About", "1950-03-25", 6);
        film7 = new Film(0, "G", "About", "1950-03-25", 7);
        film8 = new Film(0, "H", "About", "1950-03-25", 8);
        film9 = new Film(0, "I", "About", "1950-03-25", 9);
        film10 = new Film(0, "J", "About", "1950-03-25", 10);
        film11 = new Film(0, "K", "About", "1950-03-25", 11);
        film12 = new Film(0, "L", "About", "1950-03-25", 12);
    }

    @Test
    void getTop3Films() {
        // film 1 - 3 лайка, film 2 - 4 лайка, film3 - 1 лайк
        filmService.save(film1);
        filmService.save(film2);
        filmService.save(film3);

        filmService.addLike(film1, 1);
        filmService.addLike(film1, 2);
        filmService.addLike(film1, 3);

        filmService.addLike(film2, 1);
        filmService.addLike(film2, 2);
        filmService.addLike(film2, 3);
        filmService.addLike(film2, 4);

        filmService.addLike(film3, 1);

        Collection<Film> topThree = new ArrayList<>();
        topThree.add(film2);
        topThree.add(film1);
        topThree.add(film3);

        assertEquals(topThree, filmService.getTopFilms(3), "не удалось сформировать топ3 фильмов");
    }
}