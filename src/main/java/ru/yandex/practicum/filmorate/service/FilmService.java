package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Stream;

@Service
public class FilmService {
    private Film film;
    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private TreeMap<Integer, Integer> likes;

    @Autowired
    public FilmService(Film film, FilmStorage filmStorage, UserStorage userStorage) {
        this.film = film;
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int idOfUser) {
        film.getLikes().add(idOfUser);
    }

    public void deleteLike(int idOfUser) {
        film.getLikes().remove(idOfUser);
    }

    public void getTop10Films() {
        Stream<Film> filmsStream = filmStorage.getFilms().stream(); // создали стрим, записали в переменную
        filmsStream
               .sorted(new FilmComparator())
        ;
    }

    private class FilmComparator implements Compaturator<Film> {
        @Override
        public int compare(Film film, Film t1) {
            return film.getLikes().size() - t1.getLikes().size();
        }
    }
}

        //
//        TreeMap<Integer, Integer> likes = new TreeMap<>();
//        for (Film film : filmStorage.getFilms()) {
//            if (film.getLikes() != null)
//            likes.put(film.getId(), film.getLikes().size());
//        }
//        likes.values().stream().sorted();
//        return null;
//    }



//    Пусть пока каждый пользователь может поставить лайк фильму только один раз.

