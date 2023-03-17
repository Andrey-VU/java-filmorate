package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.service.ValidateFilmAndUser;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private final InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    @PostMapping()
    public Film makeNewFilm(@Valid @RequestBody Film film) {
        validator.filmValidate(film);
        inMemoryFilmStorage.save(film);
        log.info("В базу добавлен новый фильм" + inMemoryFilmStorage.getFilmById(film.getId()).toString());
        return inMemoryFilmStorage.getFilmById(film.getId());
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        validator.filmValidate(film);
        inMemoryFilmStorage.update(film);
        log.info("Информация о фильме " + inMemoryFilmStorage.getFilmById(film.getId()).toString() + "обновлена");
        return inMemoryFilmStorage.getFilmById(film.getId());
    }

    @GetMapping()
    public Collection<Film> getFilms() {
        log.info("Количество фильмов в хранилище " + inMemoryFilmStorage.getFilms().size());
        return inMemoryFilmStorage.getFilms();
    }
}



