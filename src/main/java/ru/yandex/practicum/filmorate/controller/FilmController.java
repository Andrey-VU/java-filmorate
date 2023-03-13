package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.ValidateFilmAndUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@Validated
public class FilmController {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private int idForFilms;                                               // id фильма
    private HashMap<Integer, Film> films = new HashMap<>();               // хранилище фильмов
    private List<Film> listOfFilms = new ArrayList<>();                   // список всех фильмов

    @PostMapping(value = "/films")                                        // добавление фильма
    public Film makeNewFilm(@RequestBody Film film) {
        if (validator.filmValidate(film)) {
            Film newFilm = new Film(++idForFilms, film.getName(), film.getDescription(),
                    film.getReleaseDate(), film.getDuration());
            films.put(newFilm.getId(), newFilm);
            if (!listOfFilms.contains(newFilm)) {
                listOfFilms.add(newFilm);
            }
            log.info("В базу добавлен новый фильм" + newFilm.toString());
            return newFilm;
        } else return null;
    }

    @PutMapping(value = "/films")                           // обновление фильма
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (validator.filmValidate(film)) {
            try {
                if (!films.containsKey(film.getId())) {
                    throw new ValidationException("Фильма с таким id не существует. Обновление невозможно");
                } else {
                    films.put(film.getId(), film);
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if (listOfFilms.contains(film.getId())) {
                listOfFilms .remove(film.getId());
                listOfFilms.add(film);
            }
            log.info("Информация о фильме " + film.toString() + "обновлена");
            return films.get(film.getId());
        }
        return null;
    }

    @GetMapping("/films")                                 // получение списка всех фильмов
    public List<Film> getFilms() {
        log.info("Количество фильмов в хранилище " + listOfFilms.size());
        return listOfFilms;
    }
}



