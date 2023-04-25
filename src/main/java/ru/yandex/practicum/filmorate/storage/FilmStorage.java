package ru.yandex.practicum.filmorate.storage;

import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    void save(Film film);

    void update(@NotNull Film film) throws ValidationException;

    Optional<Film> getFilmById(int id);

    int generateId();

    Collection<Film> getFilms();
}
