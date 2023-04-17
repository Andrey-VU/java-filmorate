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

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest
public class FilmControllerTest {
    private FilmController controller;
    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmDbStorage filmSbStorage;

    @BeforeEach
    public void beforeAll() {
        controller = new FilmController(filmService);
    }

//    @Test
//    public void shouldGetFilms() {
//        Film film1 = new Film("Assa", "About",
//                "1900-03-25", 120, new Rate(1, "G"));
//        Film film2 = new Film("Assa2", "About2",
//                "1900-03-25", 120, new Rate(2, "PG"));
//        Film film3 = new Film("Assa3", "About2",
//                "1900-03-25", 120, new Rate(3, "PG-13"));
//
//
//        Film filmFromStorage1 = controller.makeNewFilm(film1);
//        Film filmFromStorage2 = controller.makeNewFilm(film2);
//        Film filmFromStorage3 = controller.makeNewFilm(film3);
//
//        Collection<Film> collectionFilms = new ArrayList<>();
//        collectionFilms.add(filmFromStorage1);
//        collectionFilms.add(filmFromStorage2);
//        collectionFilms.add(filmFromStorage3);
//
//        assertEquals(collectionFilms, controller.getFilms(), "Фильмы не удалось положить/достать из хранилища");
//    }


    @Test
    public void shouldMakeAndReturnFilm() {
        Film testFilm = new Film("Assa", "About",
                "1900-03-25", 120, new Rate(1, "G"));
        Film filmFromStorage = controller.makeNewFilm(testFilm);
        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

    @Test
    public void shouldUpdateFilm() {
        Film film = new Film("Assa", "About",
                "1900-03-25", 120, new Rate(1, "G"));
        Film filmFromStorage = controller.makeNewFilm(film);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, new Rate(2, "PG"));
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }



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
