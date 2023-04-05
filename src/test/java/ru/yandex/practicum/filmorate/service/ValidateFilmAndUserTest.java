package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateFilmAndUserTest {
    ValidateFilmAndUser validateUser;
    ValidateFilmAndUser validateFilm;
    User user;
    Film film;

    @BeforeEach
    void beforeEach() {
        validateUser = new ValidateFilmAndUser();
        user = new User(0, "qw@qw.ru", "Login", "Name", "1968-03-25");
        validateFilm = new ValidateFilmAndUser();
        film = new Film(0, "Assa", "About", "1968-03-25", 120, null, null);
    }

    @Test
    void loginUser() {
        user.setLogin("");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setLogin("    ");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setLogin("a b");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setLogin(" ab");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setLogin("ab");
        assertEquals("ab", user.getLogin(), "Не удалось создать корректный логин");
    }

    @Test
    void birthDateUser() {
        user.setBirthday(LocalDate.now().plusDays(1).toString());
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setBirthday("2446-08-20");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));

        user.setBirthday(null);
        assertNull(user.getBirthday(), "Не допускает создания пустой даты рождения");
        user.setBirthday("");
        assertEquals("", user.getBirthday(), "Не допускает создания пустой даты рождения");
    }

    @Test
    void emailUser() {
        user.setEmail("");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setEmail("A@");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setEmail("@A");
        assertThrows(ValidationException.class, () -> validateUser.userValidate(user));
        user.setEmail("q@q.ru");
        assertEquals("q@q.ru", user.getEmail(), "Корректный email не создан");
    }

    @Test
    void dataRealiseFilm() {
        film.setReleaseDate("1884-12-12");
        assertThrows(ValidationException.class, () -> validateFilm.filmValidate(film));
        film.setReleaseDate("1984-12-12");
        assertEquals("1984-12-12", film.getReleaseDate(), "Корректная дата релиза не принята");
        film.setReleaseDate("1900-3-25");
        assertEquals("1900-3-25", film.getReleaseDate(), "Корректная дата релиза не принята");
        film.setReleaseDate(null);
        assertNull(film.getReleaseDate(), "Фильм без даты не создан");
    }

    @Test
    void nameFilm() {
        film.setName("");
        assertThrows(ValidationException.class, () -> validateFilm.filmValidate(film));
        film.setName("   ");
        assertThrows(ValidationException.class, () -> validateFilm.filmValidate(film));

    }

    @Test
    void descriptionFilm() {
        String string201 = "";
        for (int i = 0; i < 201; i++) {
            string201 += "i";
        }
        film.setDescription(string201);
        assertThrows(ValidationException.class, () -> validateFilm.filmValidate(film));

        String string10 = "";
        for (int i = 0; i < 10; i++) {
            string10 += "i";
        }
        film.setDescription(string10);
        assertEquals(string10, film.getDescription(), "описание фильма корректной длинны не создано");
    }

    @Test
    void durationFilm() {
        film.setDuration(-11);
        assertThrows(ValidationException.class, () -> validateFilm.filmValidate(film));
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> validateFilm.filmValidate(film));
        film.setDuration(10);
        assertEquals(10, film.getDuration(), "Не удалось задать корректную продолжительность фильма");
    }
}
