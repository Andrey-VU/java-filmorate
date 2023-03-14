package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repo.FilmsRepository;
import ru.yandex.practicum.filmorate.service.ValidateFilmAndUser;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
@Validated
public class FilmController {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private final FilmsRepository filmsRepository = new FilmsRepository();

    @PostMapping()                                        // добавление фильма
    public Film makeNewFilm(@Valid @RequestBody Film film) {
        if (validator.filmValidate(film)) {
            filmsRepository.save(film);
        }
        log.info("В базу добавлен новый фильм" + filmsRepository.getFilmById(film.getId()).toString());
        return filmsRepository.getFilmById(film.getId());
    }

    @PutMapping()                           // обновление фильма
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (validator.filmValidate(film)) {
            filmsRepository.update(film);
        }
        log.info("Информация о фильме " + filmsRepository.getFilmById(film.getId()).toString() + "обновлена");
        return filmsRepository.getFilmById(film.getId());
  }

    @GetMapping()                                 // получение списка всех фильмов
    public Collection<Film> getFilms() {
        log.info("Количество фильмов в хранилище " + filmsRepository.getFilms().size());
        return filmsRepository.getFilms();
    }
}



