package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rate;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public void save(Film film) {
        String sqlQueryFilm = "insert into films(name, description, releaseDate, duration, rate_id) " +
                "values (?, ?, ?, ?, ?)" ;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryFilm, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setString(3, film.getReleaseDate());
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRate().getRateId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(@NotNull Film film) throws ValidationException {

    }

    @Override
    public Optional<Film> getFilmById(int id) {

        SqlRowSet filmsRows = jdbcTemplate.queryForRowSet("SELECT f.*, rm.rate_name " +
                "FROM films AS f JOIN rate_mpa AS rm ON f.rate_id = rm.rate_id " +
                "WHERE f.film_id = ?", id);

        if (filmsRows.next()) {
            Film film = new Film(
                    filmsRows.getInt("film_id"),
                    filmsRows.getString("name"),
                    filmsRows.getString("description"),
                    filmsRows.getString("releasedate"),
                    filmsRows.getInt("duration"),
                    new Rate(filmsRows.getInt("rate_id"),
                            filmsRows.getString("rate_name")));

            log.info("Найден фильм: {} {}", film.getId(), film.getName());

            return Optional.of(film);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    @Override
    public int generateId() {
        return 0;
    }

    @Override
    public Collection<Film> getFilms() {
        return null;
    }


}
