package ru.yandex.practicum.filmorate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


@SpringBootTest
public class FilmControllerTest {
    private FilmController controller;
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmStorage filmStorage;

    @BeforeEach
    public void beforeEach() {
        controller = new FilmController(filmService);
    }

    @Test
    public void shouldMakeFilm(){
        Film testFilm = new Film(0, "Assa", "About",
                "1900-03-25", 120);
        Film filmFromStorage = controller.makeNewFilm(testFilm);
        testFilm = new Film(filmFromStorage.getId(), "Assa", "About",
                "1900-03-25", 120);
        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

    @Test
    public void shouldUpdateFilm() {
        Film film = new Film(0, "Assa", "About", "1900-03-25", 120);
        Film filmFromStorage = controller.makeNewFilm(film);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated",
                "New film update decription",
                "1989-04-17", 190);
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }

    @Test
    public void shouldCatchExceptionWhenUpdateFilmWithIncorrectId() {
        Film film = new Film(0, "Assa", "About", "1900-03-25", 120);
        Film filmFromStorage = controller.makeNewFilm(film);
        Film updateForFilm = new Film(filmFromStorage.getId() + 100, "Film Updated",
                "New film update decription",
                "1989-04-17", 190);
        assertThrows(ValidationException.class, () -> controller.updateFilm(updateForFilm));
    }
}
