package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
public class Film {
    private int id;
    private Set<Integer> likes;           //  хранилище id тех, кто отметил фильм понравившимся
    private String name;
    private String description;
    private String releaseDate;
    private int duration;

    public Film(int id, String name, String description, String releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film() {
    }
}

