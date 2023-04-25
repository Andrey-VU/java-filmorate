package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")                                         //
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id).get();
    }

    @GetMapping(value = {"/popular", "/popular/{count}"})
    public Collection<Film> getTopFilmsWithOptional(@RequestParam Optional<String> count) {
        return count.isPresent() ? filmService.getPopularFilms(Integer.parseInt(count.get())) :
                filmService.getPopularFilms(10);
    }

    @PostMapping()                                                  // +
    public Film makeNewFilm(@Valid @RequestBody Film film) {
        return filmService.save(film);
    }

    @PutMapping()                                                   // +
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping()                                                   //
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable int id, @PathVariable int userId) {
        return filmService.addLike(filmService.getFilmById(id).get(), userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        return filmService.deleteLike(getFilmById(id), userId);
    }
}



