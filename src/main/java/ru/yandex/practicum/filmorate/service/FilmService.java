package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

@Service
public class FilmService {
    private Film film;
    private FilmStorage filmStorage;

    @Autowired
    public FilmService(Film film, FilmStorage filmStorage) {
        this.film = film;
        this.filmStorage = filmStorage;
    }

    public void addLike(int idOfUser) {
        film.getLikes().add(idOfUser);
    }

    public void deleteLike(int idOfUser) {
        film.getLikes().remove(idOfUser);
    }

    public ArrayList<Film> getTop10Films() {
        ArrayList<Film> top10Films = new ArrayList<>();
        filmStorage.getFilms().stream()
                .sorted(new FilmComparator())                 // отсортировали по количеству лайков
                .limit(10)
                .forEach(top10Films::add);

        return top10Films;                                  // вернули топ 10 фильмов
    }

    class FilmComparator implements Comparator<Film>{
            public int compare(Film a, Film b){
                return a.getLikes().size() - b.getLikes().size();
            }
        }
}

//    Пусть пока каждый пользователь может поставить лайк фильму только один раз.

