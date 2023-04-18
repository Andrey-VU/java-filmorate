package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Film {
    private final Set<Integer> likes = new HashSet<>();
    private final TreeSet<Genre> genre = new TreeSet<>();
    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
    private Rate rate;

    public Film(String name, String description, String releaseDate, int duration, Rate rate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }
}


