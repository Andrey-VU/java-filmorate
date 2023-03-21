package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping()
    public Film makeNewFilm(@Valid @RequestBody Film film) {
        return filmService.save(film);
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping()
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable int id, @PathVariable int userId){

    }

//    PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
//    DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
//            GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков.
//    Если значение параметра count не задано, верните первые 10.


}



