package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
//@Builder
@NoArgsConstructor
public class Film {
    private final Set<Integer> likes = new HashSet<>();
    private TreeSet<Genre> genre = new TreeSet<>();
    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
    private Rate rate;

    public Film(String name, String description, String releaseDate, int duration, Rate rate) {
//        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }

    public class Genre {
        private Map<Integer,String> allGenres = new HashMap<>();
        private Integer id;
        private String name;

        private Genre(int id, String name) {
            allGenres.put(1, "Комедия");
            allGenres.put(2, "Драма");
            allGenres.put(3, "Мультфильм");
            allGenres.put(4, "Триллер");
            allGenres.put(5, "Документальный");
            allGenres.put(6, "Боевик");
            this.id = id;
            this.name = name;
        }

        public int getGenreId() {
            return id;
        }

        public String getGenreName() {
            return name;
        }

        public void setGenreId(Integer id) {
            this.id = id;
            this.name = allGenres.get(id);
        }
    }

}


