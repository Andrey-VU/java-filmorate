package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
public class UserService {
    private ValidateFilmAndUser validator = new ValidateFilmAndUser();
    private UserStorage userStorage;

    @Autowired           // сообщаем Spring, что нужно передать в конструктор объект класса User
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User save(User user) {
        validator.userValidate(user);
        userStorage.save(user);
        log.info("Зарегистрирован новый пользователь" + userStorage.getUserById(user.getId()).toString());
        return userStorage.getUserById(user.getId()).get();   // .get() распаковываем optional объект
    }

    public User updateUser(User user) {
        validator.userValidate(user);
        userStorage.update(user);
        log.info("Пользователь " + userStorage.getUserById(user.getId()).toString() + "был обновлён");
        return userStorage.getUserById(user.getId()).get();   // .get() распаковываем optional объект
    }

    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + userStorage.getUsers().size());
        return userStorage.getUsers();
    }

    public Collection<User> addFriends(int idUser, int idFriend) {
        validator.idValidate(idFriend);
        validator.idValidate(idUser);
        User user = userStorage.getUserById(idUser).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idUser + " не найден"));
        User friend = userStorage.getUserById(idFriend).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idFriend + " не найден"));
        user.getFriends().add(idFriend);
        friend.getFriends().add(idUser);        // обеспечиваем взаимность
        return getFriends(idUser);
    }

    public void deleteFromFriends(int idUser, int idFriend) {
        User user = userStorage.getUserById(idUser).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idUser + " не найден"));
        User friend = userStorage.getUserById(idFriend).orElseThrow(() -> new NullPointerException("Пользователь id "
                + idFriend + " не найден"));
        user.getFriends().remove(idFriend);
        friend.getFriends().remove(idUser);     // обеспечиваем взаимность
    }

    public Collection<User> getCommonFriends(int id1, int id2) {
        Collection<User> commonFriends = new ArrayList<>();
        User user2 = userStorage.getUserById(id2).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id2 + " не найден"));
        User user1 = userStorage.getUserById(id1).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id1 + " не найден"));

        for (Integer friend : user2.getFriends()) {
            if (user1.getFriends().contains(friend)) {
                commonFriends.add(getUserById(friend));
            }
        }
        return commonFriends;
    }

    public Collection<User> getFriends(int id) {
        User user = userStorage.getUserById(id).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id + " не найден"));
        Collection<User> friends = new ArrayList<>();
        for (Integer idOfFriends : user.getFriends()) {
            friends.add(getUserById(idOfFriends));
        }
        return friends;
    }

    public User getUserById(int id) {
        User user = userStorage.getUserById(id).orElseThrow(() -> new NullPointerException("Пользователь id "
                + id + " не найден"));
        return user;
    }
}