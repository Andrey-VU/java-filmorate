package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    @Autowired
    private UserService userService;
    @Autowired
    private GenreService genreService;

    public FilmService(FilmDbStorage filmDbStorage) {
        this.filmDbStorage = filmDbStorage;
    }

    public Film save(Film film) {
        validator.filmValidate(film);
        filmDbStorage.save(film);
        if (film.getGenres() != null) {
            genreService.saveGenres(film);
        }

        log.info("В базу добавлен новый фильм " + getFilmById(film.getId()).toString());
        return filmDbStorage.getFilmById(film.getId()).get();
    }

    public Film update(Film film) {
        validator.filmValidate(film);
        filmDbStorage.update(film);
        genreService.dropGenres(film);
        genreService.saveGenres(film);
        log.info("Информация о фильме " + getFilmById(film.getId()).toString() + "обновлена");
        return getFilmById(film.getId()).get();
    }

    public Optional<Film> getFilmById(int id) {
        validator.idValidate(id);
        return filmDbStorage.getFilmById(id);
    }

    public Collection<Film> getFilms() {
        log.info("Количество фильмов в хранилище " + filmDbStorage.getFilms().size());
        return filmDbStorage.getFilms();
    }

    public Film addLike(Film film, int idOfUser) {
        userService.getListOfUsers().contains(idOfUser);
        validator.idValidate(idOfUser);
        film.getLikes().add(idOfUser);
        filmDbStorage.addLikes(film.getId(), idOfUser);
        update(film);
        return getFilmById(film.getId()).get();
    }

    public Film deleteLike(Film film, int idOfUser) {
        validator.idValidate(idOfUser);
        film.getLikes().remove(idOfUser);
        filmDbStorage.deleteLikes(film.getId(), idOfUser);
        return filmDbStorage.getFilmById(film.getId()).get();
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmDbStorage.getPopularFilms(count);

    }
}