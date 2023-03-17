package ru.yandex.practicum.filmorate.storage;

import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    void save(Film film);

    void update(@NotNull Film film) throws ValidationException;

    Film getFilmById(int id);

    int generateId();

    Collection<Film> getFilms();
}
