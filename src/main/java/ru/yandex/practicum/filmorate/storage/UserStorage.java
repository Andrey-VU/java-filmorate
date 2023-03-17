package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    int generateId();

    void update(User user);

    void save(User user);

    User getUserById(int id);

    Collection<User> getUsers();
}
