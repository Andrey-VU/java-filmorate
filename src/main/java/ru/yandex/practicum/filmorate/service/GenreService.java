package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenresDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class GenreService {
    private final GenresDbStorage genresDbStorage;

    public GenreService(GenresDbStorage genresDbStorage) {
        this.genresDbStorage = genresDbStorage;
    }

    public Collection<Genre> getGenres() {
        return genresDbStorage.getGenres();
    }

    public Optional<Genre> getGenreById(int id) {
        return genresDbStorage.getGenreById(id);
    }

    public void saveGenres(Film film) {
        genresDbStorage.saveGenres(film);
    }
    public void dropGenres(Film film) {
        genresDbStorage.dropGenres(film);
    }
    public Collection<Genre> makeGenresCollection(int id) {
        return genresDbStorage.makeGenresCollection(id);
    }
}