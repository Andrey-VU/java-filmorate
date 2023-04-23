package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

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
                "1900-03-25", 120, 1);
        Film filmFromStorage = controller.makeNewFilm(testFilm);
        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

    @Test
    public void shouldUpdateFilm() {
        Film film = new Film("Assa", "About",
                "1900-03-25", 120, 1);
        Film filmFromStorage = controller.makeNewFilm(film);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, 2);
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }

    @Test
    public void shouldMakeAndReturnFilmWithGenres() {
        Genre genre1 = new Genre(1);
        Genre genre2 = new Genre(2);
        Collection<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        Film testFilm = new Film("Assa", "About",
                "1900-03-25", 120, 1, genres);
        Film filmFromStorage = controller.makeNewFilm(testFilm);
        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

    @Test
    public void shouldUpdateFilmWithGenre() {
        Genre genre1 = new Genre(1);
        Genre genre2 = new Genre(2);
        Genre genre4 = new Genre(4);
        Collection<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        Film film = new Film("Assa", "About", "1900-03-25", 120, 1, genres);
        Film filmFromStorage = controller.makeNewFilm(film);

        Genre genre3 = new Genre(3);
        genres.add(genre3);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, 2, genres);
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }

    @Test
    public void shouldUpdateFilmWithTwoGenreToFilmWithOneGenre() {
        Genre genre1 = new Genre(1);
        Genre genre2 = new Genre(2);
        Collection<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        Film film = new Film("Assa", "About", "1900-03-25", 120, 1, genres);
        Film filmFromStorage = controller.makeNewFilm(film);

        genres.remove(genre1);
        Collection<Genre> genres1 = new HashSet<>();
        genres1.addAll(genres);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Assa", "About", "1900-03-25",
                120, 1,  genres1);
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }

    @Test
    public void shouldUpdateFilmWithDuplicateGenre() {
        Genre genre1 = new Genre(1);
        Genre genre2 = new Genre(2);
        Collection<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        Collection<Genre> genres2 = new HashSet<>();
        genres2.addAll(genres);
        Film film = new Film("Assa", "About", "1900-03-25", 120, 1, genres);
        Film filmFromStorage = controller.makeNewFilm(film);

        genres2.add(genre2);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, 2, genres);
        Film updateForFilm2 = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, 2, genres2);
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm2);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }


    @Test
    public void shouldUpdateFilmWithDelGenre() {
        Genre genre1 = new Genre(1);
        Genre genre2 = new Genre(2);
        Collection<Genre> genres = new HashSet<>();
        genres.add(genre1);
        genres.add(genre2);

        Film film = new Film("Assa", "About", "1900-03-25", 120, 1, genres);
        Film filmFromStorage = controller.makeNewFilm(film);

        genres.remove(genre2);
        Film updateForFilm = new Film(filmFromStorage.getId(), "Film Updated", "New description",
                "1989-04-17", 190, 2, genres);
        Film fromStorageAfterUpdate = controller.updateFilm(updateForFilm);
        assertEquals(updateForFilm, fromStorageAfterUpdate, "Фильм не удалось обновить");
    }



    @Test
    public void shouThrowNullPointerWhenIncorrectId() {
        assertThrows(NullPointerException.class, () -> controller.getFilmById(222));
    }

    @Test
    public void shouldThrowNullPointerWhenUpdateFilmWhithIncorrectId() {
        Film film0 = new Film(0, "Assa", "About", "1900-03-25", 120, 2);
        controller.makeNewFilm(film0);
        Film film999 = new Film(999, "Assa2", "About", "1900-03-25", 1, 2);
        assertThrows(NullPointerException.class, () -> controller.updateFilm(film999));
    }

    @Test
    public void shouldGetFilms() {
        Film film1 = new Film("Assa", "About",
                "1900-03-25", 120, 1);
        Film film2 = new Film("Assa2", "About2",
                "1900-03-25", 120, 2);
        Film film3 = new Film("Assa3", "About2",
                "1900-03-25", 120, 3);


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
