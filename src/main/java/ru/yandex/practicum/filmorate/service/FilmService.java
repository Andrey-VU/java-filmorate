package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

@Service
@Slf4j
public class FilmService {
    private FilmStorage filmStorage;
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    public Film save(Film film) {
        validator.filmValidate(film);
        filmStorage.save(film);
        log.info("В базу добавлен новый фильм" + getFilmById(film.getId()).toString());
        return filmStorage.getFilmById(film.getId()).get();
    }

    public Film update(Film film) {
        validator.filmValidate(film);
        filmStorage.update(film);
        log.info("Информация о фильме " + getFilmById(film.getId()).toString() + "обновлена");
        return filmStorage.getFilmById(film.getId()).get();
    }

    public Collection<Film> getFilms() {
        log.info("Количество фильмов в хранилище " + filmStorage.getFilms().size());
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        validator.idValidate(id);
        Film film = filmStorage.getFilmById(id).orElseThrow(() -> new NullPointerException("Фильм с id "
                + id + " не найден"));
        return film;
    }

    public void addLike(Film film, int idOfUser) {
        validator.idValidate(idOfUser);
        film.getLikes().add(idOfUser);
        filmStorage.update(film);
    }

    public Film deleteLike(Film film, int idOfUser) {
        validator.idValidate(idOfUser);
        film.getLikes().remove(idOfUser);
        filmStorage.update(film);
        return filmStorage.getFilmById(film.getId()).get();
    }

    public Collection<Film> getTopFilms(int count) {
        ArrayList<Film> topFilms = new ArrayList<>();
        filmStorage.getFilms().stream()
                .sorted(new FilmComparator())                 // отсортировали по количеству лайков
                .limit(count)
                .forEach(topFilms::add);
        return topFilms;                                  // вернули топ-лист фильмов
    }

    class FilmComparator implements Comparator<Film>{
            public int compare(Film a, Film b){
                return b.getLikes().size() - a.getLikes().size();
            }
        }
}