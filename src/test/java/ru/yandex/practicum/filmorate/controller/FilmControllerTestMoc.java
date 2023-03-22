package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ComponentScan(value = {"ru.yandex.practicum.filmorate"})
class FilmControllerTestMoc {

    @Autowired
    private FilmController filmController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Film correctFilm;
    private Film incorrectFilm;


    @BeforeEach
    public void init() {
        correctFilm = Film.builder()
                .name("Correct Movie")
                .description("A wonderful film about the life of programmers")
                .releaseDate("2023-01-01")
                .duration(100)
                .build();

        incorrectFilm = Film.builder()
                .name("")
                .description("Description")
                .releaseDate("1900-03-25")
                .duration(200)
                .build();
    }

    @Test
    void shouldBeReturnStatusOkAndId1() throws Exception {
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(correctFilm))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void shouldBeReturnStatusBadRequest() throws Exception {
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(incorrectFilm))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void shouldBeReturnStatusNotFoundWhenIdIncorrect() throws Exception {
//        mockMvc.perform(post("/users")
//                        .content(objectMapper.writeValueAsString(filmController.getFilmById(2)))
//                        .contentType("application/json"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id").value("1"));
//    }


}