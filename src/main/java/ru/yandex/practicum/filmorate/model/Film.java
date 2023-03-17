package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Film {
    private int id;
    private Set<Integer> likes;           //  хранилище id тех, кто отметил фильм понравившимся
    private String name;
    private String description;
    private String releaseDate;
    private int duration;
}

