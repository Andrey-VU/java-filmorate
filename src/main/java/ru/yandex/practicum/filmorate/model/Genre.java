package ru.yandex.practicum.filmorate.model;

import java.util.HashMap;
import java.util.Map;

public class Genre {
    private Map<Integer, String> allGenres = new HashMap<>();
    private Integer id;
    private String name;

    public Genre(int id, String name) {
        allGenres.put(1, "Комедия");
        allGenres.put(2, "Драма");
        allGenres.put(3, "Мультфильм");
        allGenres.put(4, "Триллер");
        allGenres.put(5, "Документальный");
        allGenres.put(6, "Боевик");
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setGenreId(Integer id) {
        this.id = id;
        this.name = allGenres.get(id);
    }
}
