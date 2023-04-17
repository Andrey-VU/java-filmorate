package ru.yandex.practicum.filmorate.model;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private final Set<Integer> friends = new HashSet<>();          // поле для хранения "друзей"
    private int id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    @NonNull
    private String name;
    @NonNull
    private String birthday;
}


/*
User
Добавьте статус для связи «дружба» между двумя пользователями:
неподтверждённая — когда один пользователь отправил запрос на добавление другого пользователя в друзья,
подтверждённая — когда второй пользователь согласился на добавление.

 */
