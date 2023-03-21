package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserControllerTest {
    UserController userController;
    @Autowired
    UserService userService;
    @Autowired
    UserStorage userStorage;

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
        User user1 = new User(0, "qw@qw.ru", "Login", "", "1900-03-25");
        User user2 = new User(0, "amil@mail.ru", "Login", "   ", "1900-03-25");
        User user3 = new User(0, "test@mail.ru", "dolore", "lodore","1996-08-20");
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
    public void shouldMakeUser(){
        User user = new User(0, "qw@qw.ru", "Login",
                "", "1900-03-25");
        userController.makeNewUser(user);
        assertEquals(user.getLogin(), user.getName(), "Не удалось создать имя - копию логина");
        User user2 = new User(0, "qw@qw.ru", "Login",
                "   ", "1900-03-25");
        userController.makeNewUser(user2);
        assertEquals(user2.getLogin(), user2.getName(), "Не удалось создать имя - копию логина");
        User user3 = new User(0, "qw@qw.ru", "Login", null
                , "1900-03-25");
        userController.makeNewUser(user3);
        assertEquals(user3.getLogin(), user3.getName(), "Не удалось создать имя - копию логина");
    }

    @Test
    public void shouldMakeUserFromPostmanTest() {
        User userFromPostmanTest = new User();
        userFromPostmanTest.setLogin("dolore");
        userFromPostmanTest.setName("");
        userFromPostmanTest.setEmail("test@mail.ru");
        userFromPostmanTest.setBirthday("2446-08-20");
        assertThrows(ValidationException.class, () -> userController.makeNewUser(userFromPostmanTest));
    }

//    @Test
//    public void shouldGetUserById() {
//
//    }



}
