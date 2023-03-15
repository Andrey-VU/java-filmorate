package ru.yandex.practicum.filmorate.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repo.UsersRepository;
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
    private final UsersRepository usersRepository = new UsersRepository();

    @PostMapping()
    public User makeNewUser(@Valid @RequestBody User user) {
        validator.userValidate(user);
        usersRepository.save(user);
        log.info("Зарегистрирован новый пользователь" + usersRepository.getUserById(user.getId()).toString());
        return usersRepository.getUserById(user.getId());
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        validator.userValidate(user);
        usersRepository.update(user);
        log.info("Пользователь " + usersRepository.getUserById(user.getId()).toString() + "был обновлён");
        return usersRepository.getUserById(user.getId());
    }

    @GetMapping()
    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + usersRepository.getUsers().size());
        return usersRepository.getUsers();
    }
}
