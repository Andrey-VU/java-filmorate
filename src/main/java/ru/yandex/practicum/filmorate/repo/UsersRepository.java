package ru.yandex.practicum.filmorate.repo;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

public class UsersRepository {
    private int id;
    public final HashMap<Integer, User> users = new HashMap<>();   // хранилище пользователей

    public int generateId() {
        return ++id;
    }

    public void update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с таким id не существует. Обновление не возможно");
        } else users.put(user.getId(), user);

    }

    public void save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
