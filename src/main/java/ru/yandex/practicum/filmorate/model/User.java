package ru.yandex.practicum.filmorate.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    private int id;
    private Set<Integer> friends;          // поле для хранения "друзей"
    @NotNull
    private String email;
    private String login;
    private String name;
    @NotNull
    private String birthday;
}
