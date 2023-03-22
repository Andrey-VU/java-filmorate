package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    int generateId();

    void update(User user);

    void save(User user);

    Optional<User> getUserById(int id);

    Collection<User> getUsers();
}
