package ru.yandex.practicum.filmorate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


@SpringBootTest
public class FilmControllerTest {
    private FilmController controller;
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmDbStorage filmSbStorage;

    @BeforeEach
    public void beforeEach() {
        controller = new FilmController(filmService);
    }

    @Test
    public void shouldMakeFilm() {
        Film testFilm = new Film("Assa", "About",
                "1900-03-25", 120, new Rate(1, "G"));
        Film filmFromStorage = controller.makeNewFilm(testFilm);

        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

//    @Test
//    public void shouldUpdateFilm() {
//        Film film = new Film(0, "Assa", "About", "1900-03-25", 120, null, null);
//        Film filmFromStorage = controller.makeNewFilm(film);
//        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated",
//                "New film update decription",
//                "1989-04-17", 190, null, null);
//        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
//        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
//    }
//
//    @Test
//    public void shouThrowNullPointerWhenIncorrectId() {
//        assertThrows(NullPointerException.class, () -> controller.getFilmById(222));
//    }
//
//    @Test
//    public void shouldThrowNullPointerWhenUpdateUserWhithIncorrectId() {
//        Film film = new Film(0, "Assa", "About", "1900-03-25", 120, null, null);
//        controller.makeNewFilm(film);
//        Film film999 = new Film(999, "Assa2", "About", "1900-03-25", 1, null, null);
//        assertThrows(NullPointerException.class, () -> controller.updateFilm(film999));
//    }
}
