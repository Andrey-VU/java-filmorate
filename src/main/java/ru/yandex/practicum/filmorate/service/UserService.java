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
        return userStorage.getUserById(user.getId());
    }

    public User updateUser(User user) {
        validator.userValidate(user);
        userStorage.update(user);
        log.info("Пользователь " + userStorage.getUserById(user.getId()).toString() + "был обновлён");
        return userStorage.getUserById(user.getId());
    }

    public Collection<User> getListOfUsers() {
        log.info("Количество пользователей составляет " + userStorage.getUsers().size());
        return userStorage.getUsers();
    }

    public void addFriends(User user, int id) {
        user.getFriends().add(id);
        userStorage.getUserById(id).getFriends().add(user.getId());        // обеспечиваем взаимность
    }

    public void deleteFromFriends(User user, int id) {
        user.getFriends().remove(id);
        userStorage.getUserById(id).getFriends().remove(id);               // обеспечиваем взаимность
    }

    public ArrayList<Integer> getCommonFriends(User user1, User user2) {
        ArrayList<Integer> commonFriends = new ArrayList<>();
        for (Integer friend : userStorage.getUserById(user2.getId()).getFriends()) {
            if (user1.getFriends().contains(friend)) {
                commonFriends.add(friend);
            }
        }
        return commonFriends;
    }
}