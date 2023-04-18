package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {
    UserController userController;
    @Autowired
    UserService userService;
    @Autowired
    UserDbStorage userStorage;

    @Autowired
    private UserController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @BeforeEach
    private void beforeEach() {
        userController = new UserController(userService);
    }

    @AfterEach
    private void afterEach() {
        userController.getListOfUsers().clear();
    }

    @Test
    public void shouldReturnListOfUsers() {
        User user1 = new User("qw@qw.ru", "Login", "", "1900-03-25");
        User user2 = new User("amil@mail.ru", "Login", "   ", "1900-03-25");
        User user3 = new User("test@mail.ru", "dolore", "lodore", "1996-08-20");
        userController.makeNewUser(user1);
        userController.makeNewUser(user2);
        userController.makeNewUser(user3);
        Collection<User> tmpList = new ArrayList<>();
        tmpList.add(user1);
        tmpList.add(user2);
        tmpList.add(user3);
        assertEquals(tmpList.toString(), userController.getListOfUsers().toString(),
                "Список пользователей не сформирован, либо не выгуржен");
    }

    @Test
    public void shouldMakeUser() {
        User user = new User("qw@qw.ru", "Login", "", "1900-03-25");
        userController.makeNewUser(user);
        assertEquals(user.getLogin(), user.getName(), "Не удалось создать имя - копию логина");
        User user2 = new User("qw@qw.ru", "Login", "   ", "1900-03-25");
        userController.makeNewUser(user2);
        assertEquals(user2.getLogin(), user2.getName(), "Не удалось создать имя - копию логина");
        User user3 = new User("qw@qw.ru", "Login", null, "1900-03-25");
        userController.makeNewUser(user3);
        assertEquals(user3.getLogin(), user3.getName(), "Не удалось создать имя - копию логина");
    }

    @Test
    public void shouldMakeUserFromPostmanTestAndThrowException() {
        User userFromPostmanTest = new User();
        userFromPostmanTest.setLogin("dolore");
        userFromPostmanTest.setName("");
        userFromPostmanTest.setEmail("test@mail.ru");
        userFromPostmanTest.setBirthday("2446-08-20");
        assertThrows(ValidationException.class, () -> userController.makeNewUser(userFromPostmanTest));
    }

    @Test
    public void shouldGetUserById() {
        User user1 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user2 = new User(0, "amil@mail.ru", "Login", "   ", "1900-03-25");
        userController.makeNewUser(user1);
        userController.makeNewUser(user2);
        assertEquals(user1, userController.getUserById(1), "Пользователь 1 не вернулся по запросу");
        assertEquals(user2, userController.getUserById(2), "Пользователь 2 не вернулся по запросу");
    }

    @Test
    public void shouldThrowNullPointerWhenIncorrectId() {
        assertThrows(NullPointerException.class, () -> userController.getUserById(9999));
    }

    @Test
    public void shouldThrowNullPointerWhenUpdateUserWhithIncorrectId() {
        User user1 = new User("qw@qw.ru", "Login", "", "1900-03-25");
        userController.makeNewUser(user1);
        User user999 = new User(999, "qw@qw.ru", "Login", "", "1900-03-25");
        assertThrows(NullPointerException.class, () -> userController.updateUser(user999));
    }

    @Test
    public void shouldGetFriendsOfUserById() {
        Collection<User> tmpListOfFriends = new ArrayList<>();
        User user1 = new User("qw@qw.ru", "Login", "", "1900-03-25");
        User user2 = new User("amil@mail.ru", "Login", "   ", "1900-03-25");
        User user3 = new User("test@mail.ru", "dolore", "lodore", "1996-08-20");
        userController.makeNewUser(user1);
        userController.makeNewUser(user2);
        userController.makeNewUser(user3);
        tmpListOfFriends.add(user2);
        tmpListOfFriends.add(user3);
        userController.addFriend(user1.getId(), user2.getId());
        userController.addFriend(user1.getId(), user3.getId());
        assertEquals(tmpListOfFriends, userController.getFriends(user1.getId()), "Не удалось сформировать или " +
                "вернуть список друзей");
    }

    @Test
    public void shouldGetCommonFriendsOfUser() {
        Collection<User> tmpListOfCommonFriends = new ArrayList<>();
        User user1 = new User("qw@qw.ru", "Login", "", "1900-03-25");
        User user2 = new User("amil@mail.ru", "Login", "   ", "1900-03-25");
        User user3 = new User("test@mail.ru", "dolore", "lodore", "1996-08-20");
        User commonFriend = new User("cf@cf.ru", "Common", "Friend", "1999-03-25");

        userController.makeNewUser(user1);
        userController.makeNewUser(user2);
        userController.makeNewUser(user3);
        userController.makeNewUser(commonFriend);

        tmpListOfCommonFriends.add(commonFriend);

        userController.addFriend(user1.getId(), commonFriend.getId());
        userController.addFriend(user2.getId(), commonFriend.getId());
        userController.addFriend(user3.getId(), commonFriend.getId());
        assertEquals(tmpListOfCommonFriends, userController.getCommonFriends(user1.getId(), user2.getId()),
                "Не удалось сформировать или вернуть список общих друзей");
        assertEquals(tmpListOfCommonFriends, userController.getCommonFriends(user1.getId(), user3.getId()),
                "Не удалось сформировать или вернуть список общих друзей");
        assertEquals(tmpListOfCommonFriends, userController.getCommonFriends(user2.getId(), user3.getId()),
                "Не удалось сформировать или вернуть список общих друзей");
    }

    //users/1/friends/-1
    @Test
    public void shouldThrowExceptionWhenAddFriendWithIncorrectId() {
        User user1 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        userController.makeNewUser(user1);
        assertThrows(NullPointerException.class, () -> userController.addFriend(user1.getId(), -1));
        assertThrows(NullPointerException.class, () -> userController.addFriend(user1.getId(), 9999));
    }
}
