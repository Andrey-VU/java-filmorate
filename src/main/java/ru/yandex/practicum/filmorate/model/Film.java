package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;

    public Film() {
    }
}

