package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    @NotNull
    private String email;
    private String login;
    private String name;
    @NotNull
    private String birthday;
}
