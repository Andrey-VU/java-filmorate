package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class FilmService {
    private final FilmDbStorage filmDbStorage;
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();

    public FilmService(FilmDbStorage filmDbStorage) {
        this.filmDbStorage = filmDbStorage;
    }

    public Film save(Film film) {
         validator.filmValidate(film);
         filmDbStorage.save(film);
         log.info("В базу добавлен новый фильм "  + getFilmById(film.getId()).toString());
         return filmDbStorage.getFilmById(film.getId()).get();
    }

    public Optional<Film> getFilmById(int id) {
         validator.idValidate(id);
         return filmDbStorage.getFilmById(id);
    }

    public Film update(Film film) {
        validator.filmValidate(film);
        filmDbStorage.update(film);
        log.info("Информация о фильме " + getFilmById(film.getId()).toString() + "обновлена");
        return filmDbStorage.getFilmById(film.getId()).get();
    }

    public Collection<Film> getFilms() {
        log.info("Количество фильмов в хранилище " + filmDbStorage.getFilms().size());
        return filmDbStorage.getFilms();
    }



//    private FilmStorage filmStorage;
//    @Autowired
//    private UserService userService;
//    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
//
//    public FilmService(FilmStorage filmStorage) {
//        this.filmStorage = filmStorage;
//    }
//
//
//    public Collection<Film> getFilms() {
//        log.info("Количество фильмов в хранилище " + filmStorage.getFilms().size());
//        return filmStorage.getFilms();
//    }
//
//    public Film getFilmById(int id) {
//        validator.idValidate(id);
//        Film film = filmStorage.getFilmById(id).orElseThrow(() -> new NullPointerException("Фильм с id "
//                + id + " не найден"));
//        return film;
//    }
//
//    public Film addLike(Film film, int idOfUser) {
//        userService.getListOfUsers().contains(idOfUser);
//        validator.idValidate(idOfUser);
//        film.getLikes().add(idOfUser);
//        update(film);
//        return getFilmById(film.getId());
//    }
//
//    public Film deleteLike(Film film, int idOfUser) {
//        validator.idValidate(idOfUser);
//        film.getLikes().remove(idOfUser);
//        filmStorage.update(film);
//        return filmStorage.getFilmById(film.getId()).get();
//    }
//
//    public Collection<Film> getTopFilms(int count) {
//        ArrayList<Film> topFilms = new ArrayList<>();
//        filmStorage.getFilms().stream()
//                .sorted((Film a, Film b)
//                        -> b.getLikes().size() - a.getLikes().size())   // отсортировали по количеству лайков
//                .limit(count)
//                .forEach(topFilms::add);
//        return topFilms;
//    }
}