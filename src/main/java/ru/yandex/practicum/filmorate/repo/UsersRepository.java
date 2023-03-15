package ru.yandex.practicum.filmorate.repo;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
public class UsersRepository {
    private int id;
    private final HashMap<Integer, User> users = new HashMap<>();

    public int generateId() {
        return ++id;
    }

    public void update(User user) {
        if (!users.containsKey(user.getId())) {
            log.info("Id пользователя: " + user + " - не найдено. Обновление невозможно");
            throw new ValidationException("Пользователя с таким id не существует. Обновление невозможно");
        } else users.put(user.getId(), user);
    }

    public void save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
    }

    public User getUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        return null;
    }

    public Collection<User> getUsers() {
        return users.values();
    }
}
