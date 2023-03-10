package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    private int idForUsers;
    private final HashMap<Integer, User> users = new HashMap<>();                    // хранилище пользователей
    private List<User> listOfUsers = new ArrayList<>();                              // список пользователей

    @PostMapping(value = "/users")                                 //        создание пользователя;
    public User makeNewUser(@RequestBody User user) {
        if (userValidate(user)) {
            User newUser = new User(++idForUsers, user.getEmail(), user.getLogin(), user.getName(),
                    user.getBirthday());
            users.put(newUser.getId(), newUser);
            if (!listOfUsers.contains(newUser)) {
                listOfUsers.add(newUser);
            }
            log.info("Зарегистрирован новый пользователь" + newUser.toString());
            return newUser;
        } else return null;
    }

    @PutMapping(value = "/users")             //        обновление пользователя;
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (userValidate(user)) {
            try {
                if (!users.containsKey(user.getId())) {
                    throw new ValidationException("Пользователя с таким id не существует. Обновление не возможно");
                } else {
                    users.put(user.getId(), user);
                }
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if (listOfUsers.contains(user.getId())) {
                listOfUsers .remove(user.getId());
                listOfUsers.add(user);
            }
            log.info("Пользователь " + user.toString() + "был обновлён");
            return users.get(user.getId());
        }
        return null;
    }

    private boolean userValidate(@NotNull User user) throws ValidationException {
        try {
            String email = user.getEmail();
            String login = user.getLogin();
            if (email == null || email.equals("")) {
                throw new ValidationException("email не может быть пустым");
            } else if (!email.contains("@") || email.charAt(0) == "@".charAt(0)
                    || email.charAt(email.length() - 1) == "@".charAt(0)) {
                throw new ValidationException("введён не корректный email");
            } else if (login == null || login.equals("") || login.contains(" ")) {
                throw new ValidationException("логин не может быть пустым, или содержащим пробелы");
            } else if (user.getName() == null || user.getName().equals("")) {
                user.setName(login);
            } else if (user.getBirthday().isAfter(LocalDateTime.now())) {
                throw new ValidationException("введена некорректная дата рождения");
            } else return true;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            log.info("Вызвано исключение" + e.toString());
        }
        return false;
    }

    @GetMapping("/users")                                    // для получения списка пользователей
    public List<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + listOfUsers.size());
        return listOfUsers;
    }
}
