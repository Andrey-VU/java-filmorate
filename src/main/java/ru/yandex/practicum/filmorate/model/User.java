package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class User {
    private int id;
    @NotNull
    private String email;
    private String login;
    private String name;
    @NotNull
    private String birthday;

    public User() {
    }
}
