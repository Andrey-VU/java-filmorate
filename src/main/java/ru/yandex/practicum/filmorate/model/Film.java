package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private final Set<Integer> likes = new HashSet<>();   //  хранилище id тех, кто отметил фильм понравившимся
    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
}