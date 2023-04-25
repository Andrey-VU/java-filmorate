package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaControllerTest {
    private MpaController controller;
    @Autowired
    private MpaService mpaService;

    @BeforeEach
    public void beforeEach() {
        controller = new MpaController(mpaService);
    }

    @Test
    void getRateById() {
        Mpa mpa1 = new Mpa("G", 1);
        assertEquals(mpa1, controller.getMpaById(1), "MPA 1 не удалось достать из хранилища");
    }
}