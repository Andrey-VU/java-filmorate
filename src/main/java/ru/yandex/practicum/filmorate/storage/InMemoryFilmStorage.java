package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public void save(Film film) {
        film.setId(generateId());
        films.put(film.getId(), film);
    }

    @Override
    public void update(Film film){
        getFilmById(film.getId()).orElseThrow(() ->  {
                    log.error("В базе отсутствует " + film + " с указанным Id. Обновление невозможно");
                    throw new NullPointerException("Фильм с таким id не найден. Обновление невозможно");
                });
        films.put(id, film);
    }

    @Override
    public Optional<Film> getFilmById(int id) {
        return Optional.ofNullable(films.get(id));
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
