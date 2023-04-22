package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
public class Film {
    private Collection<Genre> genres = new HashSet<>();
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


//    public Film(String name, String description, String releaseDate, int duration, Mpa mpa,
//                Collection<Genre> genres) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.releaseDate = releaseDate;
//        this.duration = duration;
//        this.mpa = mpa;
//        this.genres.addAll(genres);
//    }

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


