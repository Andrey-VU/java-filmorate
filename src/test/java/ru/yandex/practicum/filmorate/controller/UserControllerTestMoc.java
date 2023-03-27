package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ComponentScan(value = {"ru.yandex.practicum.filmorate"})
public class UserControllerTestMoc {

    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private User correctUser;
    private User incorrectUser;

    @BeforeEach
    public void init() {
        correctUser = User.builder()
                .email("ab@ab.ru")
                .login("Correctlogin")
                .name("Correct User")
                .birthday("2000-01-01")
                .build();

        incorrectUser = User.builder()
                .email("@a.ru")
                .login("")
                .name("")
                .birthday("2050-01-01")
                .build();
    }

    @Test
    void shouldBeReturnStatusOkAndId1() throws Exception {
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(correctUser))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void shouldBeReturnStatusBadRequest() throws Exception {
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(incorrectUser))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void shouldBeReturnStatusNotFoundWhenIdIncorrect() throws Exception {
//        mockMvc.perform(post("/users")
//                        .content(objectMapper.writeValueAsString(userController.getUserById(2)))
//                        .contentType("application/json"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id").value("1"));
//    }
}


/*
User correctUser2 = User.builder()
                .login("doloreUpdate")
                .name("est adipisicing")
                .email("mail@yandex.ru")
                .birthday("1976-09-20")
                .build();

 */