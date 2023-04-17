package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private UserDbStorage userDbStorage;

    public UserService(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }

    public User save(User user) {
        validator.userValidate(user);
        User newUser = userDbStorage.save(user);
        log.info("Зарегистрирован новый пользователь" + userDbStorage.getUserById(user.getId()).toString());
        return newUser;
    }

    public User updateUser(User user) {
        validator.userValidate(user);
        userDbStorage.update(user);
        log.info("Пользователь " + userDbStorage.getUserById(user.getId()).toString() + "был обновлён");
        return userDbStorage.getUserById(user.getId()).get();   // .get() распаковываем optional объект
    }

    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + userDbStorage.getUsers().size());
        return userDbStorage.getUsers();
    }

    public Collection<User> addFriends(int idUser, int idFriend) {
        validator.idValidate(idFriend);
        validator.idValidate(idUser);
        User user = userDbStorage.getUserById(idUser).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idUser + " не найден"));
        User friend = userDbStorage.getUserById(idFriend).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idFriend + " не найден"));
        user.getFriends().add(idFriend);
        friend.getFriends().add(idUser);        // обеспечиваем взаимность
        return getFriends(idUser);
    }

    public void deleteFromFriends(int idUser, int idFriend) {
        User user = userDbStorage.getUserById(idUser).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idUser + " не найден"));
        User friend = userDbStorage.getUserById(idFriend).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idFriend + " не найден"));
        user.getFriends().remove(idFriend);
        friend.getFriends().remove(idUser);     // обеспечиваем взаимность
    }

    public Collection<User> getCommonFriends(int id1, int id2) {
        User user2 = userDbStorage.getUserById(id2).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id2 + " не найден"));
        User user1 = userDbStorage.getUserById(id1).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id1 + " не найден"));

        return user2.getFriends().stream()
                .filter(aCommonFriend -> user1.getFriends().contains(aCommonFriend))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public Collection<User> getFriends(int id) {
        User user = userDbStorage.getUserById(id).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id + " не найден"));

        return user.getFriends().stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public User getUserById(int id) {
        User user = userDbStorage.getUserById(id).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id + " не найден"));
        return user;
    }
}