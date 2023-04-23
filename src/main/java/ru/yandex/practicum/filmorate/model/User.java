package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    //    private final Set<User> friends = new TreeSet<>(new Comparator<User>(){
//        @Override
//        public int compare(User u1, User u2) {
//            return u1.getId() - u2.getId();
//        }
//    });          // поле для хранения "друзей"
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