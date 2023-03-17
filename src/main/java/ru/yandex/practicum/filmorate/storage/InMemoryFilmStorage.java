package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public void save(@NotNull Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
    }

    @Override
    public void update(@NotNull Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.error("В базе отсутствует " + film + " с указанным Id. Обновление невозможно");
            throw new ValidationException("Фильма с таким id не существует. Обновление не возможно");
        }
    }

    @Override
    public Film getFilmById(int id) {
        return films.get(id);
    }

    @Override
    public int generateId() {
        return ++id;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }
}
