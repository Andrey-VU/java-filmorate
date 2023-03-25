package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable int id, @PathVariable int userId){
        Film film = filmService.getFilmById(id);
        film.getLikes().add(userId);
        return filmService.update(film);
    }

    @GetMapping()
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam (required = false) String count) {
        return count == null  ? filmService.getTopFilms(10) :
                filmService.getTopFilms(Integer.parseInt(count));
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id){
        return filmService.getFilmById(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film unlikeFilm(@PathVariable int id, @PathVariable int userId){
        return filmService.deleteLike(getFilmById(id), userId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNullFilm(final NullPointerException e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException e) {
        return Map.of("error", e.getMessage());
    }
}



