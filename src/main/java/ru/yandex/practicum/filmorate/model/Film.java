package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    private final Set<Integer> likes = new HashSet<>();
    private int id;
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
}