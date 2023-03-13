package ru.yandex.practicum.filmorate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;


@SpringBootTest
public class FilmControllerTest {

    @Autowired
    private FilmController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldMakeFilm(){
        controller = new FilmController();
        Film testFilm = new Film(0, "Assa", "About",
                "1900-03-25", 120);
        Film filmFromStorage = controller.makeNewFilm(testFilm);
        testFilm = new Film(filmFromStorage.getId(), "Assa", "About",
                "1900-03-25", 120);
        assertEquals(testFilm, filmFromStorage, "Фильм не внесён в базу данных");
    }

}
