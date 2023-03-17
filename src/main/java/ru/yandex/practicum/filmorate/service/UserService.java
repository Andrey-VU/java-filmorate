package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;

@Service
public class UserService {
    private User user;
    private UserStorage userStorage;

    @Autowired           // сообщаем Spring, что нужно передать в конструктор объект класса User
    public UserService(User user, UserStorage userStorage) {
        this.user = user;
        this.userStorage = userStorage;
    }

    public void addFriends(int id) {
        user.getFriends().add(id);
        userStorage.getUserById(id).getFriends().add(user.getId());        // обеспечиваем взаимность
    }

    public void deleteFromFriends(int id) {
        user.getFriends().remove(id);
        userStorage.getUserById(id).getFriends().remove(id);               // обеспечиваем взаимность
    }

    public ArrayList<Integer> getCommonFriends(int id) {
        ArrayList<Integer> commonFriends = new ArrayList<>();
        for (Integer friend : userStorage.getUserById(id).getFriends()) {
            if (user.getFriends().contains(friend)) {
                commonFriends.add(friend);
            }
        }
        return commonFriends;
    }
}


//        Для того чтобы обеспечить уникальность значения (мы не можем добавить одного человека в друзья дважды),
//        проще всего использовать для хранения Set<Long> c id друзей.
//        Таким же образом можно обеспечить условие «один пользователь — один лайк» для оценки фильмов.