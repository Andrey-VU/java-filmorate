package ru.yandex.practicum.filmorate.storage;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int id;
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public int generateId() {
        return ++id;
    }

    @Override
    public void update(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Id пользователя: " + user + " - не найдено. Обновление невозможно");
            throw new ValidationException("Пользователя с таким id не существует. Обновление невозможно");
        } else users.put(user.getId(), user);
    }

    @Override
    public void save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
    }

    @Override
    public User getUserById(int id) {
            return users.get(id);
        }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }
}
