package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int id;
    @NonNull
    private String email;
    @NonNull
    private String login;

    private String name;
    private String birthday;

    public User(@NonNull String email, @NonNull String login, String name, String birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}