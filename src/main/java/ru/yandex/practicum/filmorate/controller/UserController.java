package ru.yandex.practicum.filmorate.controller;

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
public class UserController {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private final UsersRepository usersRepository = new UsersRepository();

    public UserController() {
    }

    @PostMapping()                                 //        создание пользователя;
    public User makeNewUser(@Valid @RequestBody User user) {
        if (validator.userValidate(user)) {
            usersRepository.save(user);
        }
        log.info("Зарегистрирован новый пользователь" + usersRepository.users.get(user.getId()).toString());
        return usersRepository.users.get(user.getId());
    }

    @PutMapping()             //        обновление пользователя;
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        if (validator.userValidate(user)) {
            usersRepository.update(user);
        }
        log.info("Пользователь " + usersRepository.users.get(user.getId()).toString() + "был обновлён");
           return usersRepository.users.get(user.getId());
    }

    @GetMapping()                                    // для получения списка пользователей
    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + usersRepository.getUsers().size());
        return usersRepository.getUsers();
    }
}
