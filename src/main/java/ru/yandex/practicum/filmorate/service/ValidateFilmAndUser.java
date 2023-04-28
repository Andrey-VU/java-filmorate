package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

@Slf4j
@Service
public class ValidateFilmAndUser {

    public void userValidate(User user) {
        String email = user.getEmail();
        String login = user.getLogin();

        if (StringUtils.isBlank(email)) {
            log.error("Недопустимое значение поля email " + email.toString());
            throw new ValidationException("email не может быть пустым");
        }
        if (StringUtils.containsNone(email, "@") || StringUtils.startsWith(email, "@")
                || StringUtils.endsWithAny(email, "@", ".")) {
            log.error("Недопустимое значение поля email " + email.toString());
            throw new ValidationException("введён не корректный email");
        }
        if (StringUtils.isBlank(login) || StringUtils.containsWhitespace(login)) {
            log.error("Недопустимое значение поля login " + login.toString());
            throw new ValidationException("логин не может быть пустым, или содержащим пробелы");
        }
        if (StringUtils.isBlank(user.getName())) {
            user.setName(login);
        }
        if (LocalDate.parse(user.getBirthday()).isAfter(LocalDate.now())) {
            log.error("Указана дата рождения из будущего " + user.getBirthday());
            throw new ValidationException("введена некорректная дата рождения");
        }
    }

    public void filmValidate(@NotNull Film film) throws ValidationException {
        LocalDate date = LocalDate.of(1895, Month.DECEMBER, 28);
        String name = film.getName();
        String description = film.getDescription();
        if (StringUtils.isBlank(name)) {
            log.error("Недопустимое значение поля name " + name);
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (description.length() > 200) {
            log.error("Превышено количество символов в описании " + description.length());
            throw new ValidationException("Описание фильма должно быть короче 200 символов");
        }
        if (film.getDuration() <= 0) {
            log.error("Введено отрицательное значение для длительности фильма " + film.getDuration());
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
        if (LocalDate.parse(film.getReleaseDate()).isBefore(date)) {
            log.error("Введена некорректная дата " + film.getReleaseDate());
            throw new ValidationException("введена некорректная дата создания фильма");
        }
    }

    public void idValidate(int id) throws NullPointerException {
        if (id <= 0) {
            log.error("Введён некорректный id " + id);
            throw new NullPointerException("id не может быть меньше или равен нулю");
        }
    }
}
