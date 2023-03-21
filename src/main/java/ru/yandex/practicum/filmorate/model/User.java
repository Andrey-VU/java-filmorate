package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
//@RequiredArgsConstructor
//@NoArgsConstructor(force = true)

public class User {
//    @NonNull                // генерирует исключения NullPointerException (так же как с помощью throw)
    private int id;
    private Set<Integer> friends;          // поле для хранения "друзей"
  //  @NonNull
    private String email;
    //@NonNull
    private String login;
   // @NonNull
    private String name;
   // @NonNull
    private String birthday;

    public User(int id, String email, String login, String name, String birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public User() {
    }
}

//@RequiredArgsConstructor генерирует конструктор,
// включающий все final-поля или поля, помеченные аннотацией @NonNull
