package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

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

    public Set<Integer> addFriends(int idUser, int idFriend) {
        userStorage.getUserById(idUser).getFriends().add(idFriend);
        userStorage.getUserById(idFriend).getFriends().add(idUser);        // обеспечиваем взаимность
        return getFriends(idUser);
    }

    public void deleteFromFriends(int idUser, int idFriend) {
        userStorage.getUserById(idUser).getFriends().remove(idFriend);
        userStorage.getUserById(idFriend).getFriends().remove(idUser);     // обеспечиваем взаимность
    }

    public ArrayList<Integer> getCommonFriends(int id1, int id2) {
        ArrayList<Integer> commonFriends = new ArrayList<>();
        for (Integer friend : userStorage.getUserById(id2).getFriends()) {
            if (userStorage.getUserById(id1).getFriends().contains(friend)) {
                commonFriends.add(friend);
            }
        }
        return commonFriends;
    }

    public Set<Integer> getFriends(int id) {
        return userStorage.getUserById(id).getFriends();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }
}