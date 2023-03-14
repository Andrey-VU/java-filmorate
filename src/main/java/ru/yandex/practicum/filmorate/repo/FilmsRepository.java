package ru.yandex.practicum.filmorate.repo;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.HashMap;

public class FilmsRepository {
    private int id = 0;                                                        // id фильма
    public final HashMap<Integer, Film> films = new HashMap<>();               // хранилище фильмов

    public void save(@NotNull Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
    }

    public void update(@NotNull Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else throw new ValidationException("Фильма с таким id не существует. Обновление не возможно");
    }

    public int generateId() {
        return ++id;
    }

    public Collection<Film> getFilms() {
        return films.values();
    }
}
