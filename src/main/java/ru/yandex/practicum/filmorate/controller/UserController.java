package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repo.UserRepository;
import ru.yandex.practicum.filmorate.service.ValidateFilmAndUser;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private final UserRepository userRepository = new UserRepository();

    public UserController() {
    }

    @PostMapping()                                 //        создание пользователя;
    public User makeNewUser(@Valid @RequestBody User user) {
        if (validator.userValidate(user)) {
            userRepository.save(user);
        }
        log.info("Зарегистрирован новый пользователь" + userRepository.users.get(user.getId()).toString());
        return userRepository.users.get(user.getId());
    }

    @PutMapping()             //        обновление пользователя;
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        if (validator.userValidate(user)) {
            userRepository.update(user);
        }
        log.info("Пользователь " + user.toString() + "был обновлён");
           return userRepository.users.get(user.getId());
    }

    @GetMapping()                                    // для получения списка пользователей
    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + userRepository.getUsers().size());
        return userRepository.getUsers();
    }
}
