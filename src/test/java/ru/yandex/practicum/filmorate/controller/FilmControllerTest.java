package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RateMpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmControllerTest {
    private FilmController controller;
    @Autowired
    private FilmService filmService;

    @BeforeEach
    public void beforeEach() {
        controller = new FilmController(filmService);
    }


    @Test
    public void shouldMakeAndReturnFilm() {
        Film testFilm = new Film("Assa", "About",
                "1900-03-25", 120, new RateMpa(1, "G"));
        Film filmFromStorage = controller.makeNewFilm(testFilm);
        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

    @Test
    public void shouldUpdateFilm() {
        Film film = new Film("Assa", "About",
                "1900-03-25", 120, new RateMpa(1, "G"));
        Film filmFromStorage = controller.makeNewFilm(film);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, new RateMpa(2, "PG"));
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }

    @Test
    public void shouThrowNullPointerWhenIncorrectId() {
        assertThrows(NullPointerException.class, () -> controller.getFilmById(222));
    }

    @Test
    public void shouldThrowNullPointerWhenUpdateFilmWhithIncorrectId() {
        Film film0 = new Film(0, "Assa", "About", "1900-03-25", 120, new RateMpa(2, "PG"));
        controller.makeNewFilm(film0);
        Film film999 = new Film(999, "Assa2", "About", "1900-03-25", 1,new RateMpa(2, "PG"));
        assertThrows(NullPointerException.class, () -> controller.updateFilm(film999));
    }

    @Test
    public void shouldGetFilms() {
        Film film1 = new Film("Assa", "About",
                "1900-03-25", 120, new RateMpa(1, "G"));
        Film film2 = new Film("Assa2", "About2",
                "1900-03-25", 120, new RateMpa(2, "PG"));
        Film film3 = new Film("Assa3", "About2",
                "1900-03-25", 120, new RateMpa(3, "PG-13"));


        Film filmFromStorage1 = controller.makeNewFilm(film1);
        Film filmFromStorage2 = controller.makeNewFilm(film2);
        Film filmFromStorage3 = controller.makeNewFilm(film3);

        Collection<Film> collectionFilms = new ArrayList<>();
        collectionFilms.add(filmFromStorage1);
        collectionFilms.add(filmFromStorage2);
        collectionFilms.add(filmFromStorage3);

        assertEquals(collectionFilms, controller.getFilms(), "Фильмы не удалось положить/достать из хранилища");
    }


}
