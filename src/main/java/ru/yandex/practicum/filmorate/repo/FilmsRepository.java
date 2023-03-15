package ru.yandex.practicum.filmorate.repo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
public class FilmsRepository {
    private int id = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    public void save(@NotNull Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
    }

    public void update(@NotNull Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.error("В базе отсутствует " + film + " с указанным Id. Обновление невозможно");
            throw new ValidationException("Фильма с таким id не существует. Обновление не возможно");
        }
    }

    public Film getFilmById(int id) {
        return films.get(id);
    }

    public int generateId() {
        return ++id;
    }

    public Collection<Film> getFilms() {
        return films.values();
    }
}
