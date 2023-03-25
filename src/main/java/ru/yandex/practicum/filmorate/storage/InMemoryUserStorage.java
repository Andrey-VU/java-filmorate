package ru.yandex.practicum.filmorate.storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private int id;
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public int generateId() {
        return ++id;
    }

    @Override
    public void update(User user) {
        getUserById(user.getId()).orElseThrow(() -> {
            log.error("В базе отсутствует " + user + " с указанным Id. Обновление невозможно");
            throw new NullPointerException("Пользователя с таким id не существует. Обновление невозможно");
        });
        users.put(id, user);
    }

    @Override
    public void save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(users.get(id));
        }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }
}
