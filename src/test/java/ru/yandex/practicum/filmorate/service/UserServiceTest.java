package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTest {
    private final JdbcTemplate jdbcTemplate;
    User firstUser;
    UserService userService;

    @BeforeEach
    public void beforeEach() {
        firstUser = new User("qw@qw.ru", "Login", "", "1900-03-25");
        userService = new UserService(new UserDbStorage(jdbcTemplate));
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

        User friendUser = new User("friend@friend.ru", "Login", "Friend", "1963-03-25");
        userService.save(firstUser);
        userService.save(friendUser);
        userService.addFriends(firstUser.getId(), friendUser.getId());

        Collection<User> friendOfFirst = new ArrayList<>();
        friendOfFirst.add(friendUser);
        Collection<User> friendOfFriend = new HashSet<>();
        friendOfFriend.add(firstUser);

        assertEquals(friendOfFirst, userService.getFriends(firstUser.getId()),
                "новый друг не добавлен");
    }

    @Test
    void shouldGetCommonFriends() {
        User friendUser = new User("friend@friend.ru", "Login", "Friend", "1963-03-25");
        User thirdUser = new User("third@user.ru", "Third", "ThirdU", "1983-01-15");
        userService.save(firstUser);
        userService.save(friendUser);
        userService.save(thirdUser);
        userService.addFriends(firstUser.getId(), friendUser.getId());
        userService.addFriends(thirdUser.getId(), friendUser.getId());
        assertEquals(userService.getCommonFriends(1, 3), userService.getCommonFriends(3, 1));
    }
}