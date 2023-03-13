package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.time.Month;

@Slf4j
@Component
public class ValidateFilmAndUser {

    public boolean userValidate(User user) {
            String email = user.getEmail();
            String login = user.getLogin();
            if (email == null || email.equals("") || email.isBlank()) {
                throw new ValidationException("email не может быть пустым");
            }
            if (!email.contains("@") || email.charAt(0) == "@".charAt(0)
                    || email.charAt(email.length() - 1) == "@".charAt(0)) {
                throw new ValidationException("введён не корректный email");
            } if (login == null || login.equals("") || login.contains(" ")) {
                throw new ValidationException("логин не может быть пустым, или содержащим пробелы");
            } if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
                user.setName(login);
            }
            if (LocalDate.parse(user.getBirthday()).isAfter(LocalDate.now())) {
                throw new ValidationException("введена некорректная дата рождения");
            }
        return true;
    }

    public boolean filmValidate(@NotNull Film film) throws ValidationException {
        LocalDate date = LocalDate.of(1895, Month.DECEMBER, 28);
            String name = film.getName();
            String description = film.getDescription();
            if (name == null || name.isBlank()) {
                throw new ValidationException("Название фильма не может быть пустым");
            } if (description.length() > 200 ) {
                throw new ValidationException("Описание фильма должно быть короче 200 символов");
            } if (film.getDuration() <= 0) {
                throw new ValidationException("Продолжительность фильма не может быть отрицательной");
            } if (LocalDate.parse(film.getReleaseDate()).isBefore(date)) {
                throw new ValidationException("введена некорректная дата создания фильма");
            }
        return true;
    }
}
