package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    User firstUser;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        firstUser = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        userService = new UserService(new InMemoryUserStorage());
    }

    @AfterEach
    public void afterEach() {
        userService.getListOfUsers().clear();
    }

    @Test
    void shouldGetUserById() {
        assertThrows(NullPointerException.class, () -> userService.getUserById(1));
        userService.save(firstUser);
        assertEquals(firstUser, userService.getUserById(1), "пользователь не вернулся из хранилища по id ");
    }

    @Test
    void shouldAddFriendsAndGetThem() {
        assertThrows(NullPointerException.class, () -> userService.getFriends(2));
        assertThrows(NullPointerException.class, () -> userService.getCommonFriends(1, 2));

        User friendUser = new User(0, "friend@friend.ru", "Login", "Friend", "1963-03-25");
        userService.save(firstUser);
        userService.save(friendUser);
        userService.addFriends(firstUser.getId(), friendUser.getId());

        Collection<User> friendOfFirst = new ArrayList<>();
        friendOfFirst.add(friendUser);
        Collection<User> friendOfFriend = new HashSet<>();
        friendOfFriend.add(firstUser);

        assertEquals(friendOfFirst, userService.getFriends(firstUser.getId()),
                "новый друг не добавлен");
        assertEquals(friendOfFriend.toString(), userService.getFriends(friendUser.getId()).toString(),
                "нарушен принцип взаимности при добавлении в друзья");
    }

    @Test
    void shouldGetCommonFriends() {
        User friendUser = new User(0, "friend@friend.ru", "Login", "Friend", "1963-03-25");
        User thirdUser = new User(0, "third@user.ru", "Third", "ThirdU", "1983-01-15");
        userService.save(firstUser);
        userService.save(friendUser);
        userService.save(thirdUser);
        userService.addFriends(firstUser.getId(), friendUser.getId());
        userService.addFriends(thirdUser.getId(), friendUser.getId());
        assertEquals(userService.getCommonFriends(1, 3), userService.getCommonFriends(3, 1));
    }
}