package ru.yandex.practicum.filmorate.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.service.ValidateFilmAndUser;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
@NoArgsConstructor
public class UserController {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private final InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @PostMapping()
    public User makeNewUser(@Valid @RequestBody User user) {
        validator.userValidate(user);
        inMemoryUserStorage.save(user);
        log.info("Зарегистрирован новый пользователь" + inMemoryUserStorage.getUserById(user.getId()).toString());
        return inMemoryUserStorage.getUserById(user.getId());
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        validator.userValidate(user);
        inMemoryUserStorage.update(user);
        log.info("Пользователь " + inMemoryUserStorage.getUserById(user.getId()).toString() + "был обновлён");
        return inMemoryUserStorage.getUserById(user.getId());
    }

    @GetMapping()
    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + inMemoryUserStorage.getUsers().size());
        return inMemoryUserStorage.getUsers();
    }
}
