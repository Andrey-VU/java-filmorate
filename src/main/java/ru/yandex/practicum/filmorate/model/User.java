package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private final Set<Integer> friends = new HashSet<>();          // поле для хранения "друзей"
    private int id;
    private String email;
    private String login;
    private String name;
    private String birthday;
}
