package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class Film {
    private final Set<Integer> likes = new HashSet<>();
    private Set<Genre> genres = new TreeSet<>(new Comparator<Genre>() {
        @Override
        public int compare(Genre g1, Genre g2) {
            return g1.getId() - g2.getId();
        }
    });
    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
    private Mpa mpa;

    public Film(String name, String description, String releaseDate, int duration, int mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = new Mpa(mpa);
    }

    public Film(String name, String description, String releaseDate, int duration, Mpa mpa,
                Collection<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres.addAll(genres);
    }

    public Film(String name, String description, String releaseDate, int duration, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;

    }

    public Film(int id, String name, String description, String releaseDate, int duration, Mpa mpa,
                Collection<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres.addAll(genres);
    }

    public Film(int id, String name, String description, String releaseDate, int duration, int mpa,
                Collection<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = new Mpa(mpa);
        this.genres.addAll(genres);
    }

    public Film(String name, String description, String releaseDate, int duration, int mpa,
                Collection<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = new Mpa(mpa);
        this.genres.addAll(genres);
    }

    public Film(int id, String name, String description, String releaseDate, int duration, int mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = new Mpa(mpa);
    }

}


