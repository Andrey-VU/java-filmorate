package ru.yandex.practicum.filmorate.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class FilmController {
    private int idForFilms;                                               // id фильма
    private HashMap<Integer, Film> films = new HashMap<>();               // хранилище фильмов
    private List<Film> listOfFilms = new ArrayList<>();                   // список всех фильмов

    @PostMapping(value = "/films")                                        // добавление фильма
    public Film makeNewFilm(@RequestBody Film film) {
        if (filmValidate(film)) {
            Film newFilm = new Film(++idForFilms, film.getName(), film.getDescription(),
                    film.getReleaseDate(), film.getDuration());
            films.put(newFilm.getId(), newFilm);
            if (!listOfFilms.contains(newFilm)) {
                listOfFilms.add(newFilm);
            }
            return newFilm;
        } else return null;
    }

    @PutMapping(value = "/films")                           // обновление фильма
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (filmValidate(film)) {
            try {
                if (!films.containsKey(film.getId())) {
                    throw new ValidationException("Пользователя с таким id не существует. Обновление не возможно");
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
            return films.get(film.getId());
        }
        return null;
    }

    private boolean filmValidate(@NotNull Film film) throws ValidationException {
        LocalDateTime dateTime = LocalDateTime.of(1985, Month.DECEMBER, 28, 24, 00);
        try {
            String name = film.getName();
            String description = film.getDescription();
            if (name == null || name.equals("")) {
                throw new ValidationException("Название фильма не может быть пустым");
            } else if (description.length() > 200 ) {
                throw new ValidationException("Описание фильма должно быть короче 200 символов");
            } else if (film.getDuration().isNegative()) {
                throw new ValidationException("Продолжительность фильма не может быть отрицательной");
            } else if (film.getReleaseDate().isBefore(dateTime)) {
                throw new ValidationException("введена некорректная дата создания фильма");
            } else return true;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    @GetMapping("/films")                                 // получение списка всех фильмов
    public List<Film> getFilms() {
        return (List<Film>) films;
    }
}



