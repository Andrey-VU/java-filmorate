package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Component
@Primary
public class UserDbStorage implements UserStorage {

    @Override
    public int generateId() {
        return 0;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<User> getUsers() {
        return null;
    }
}
