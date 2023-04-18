package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RateMpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sqlQueryFilm = "insert into films(name, description, release_date, duration, rate_id) " +
                "values (?, ?, ?, ?, ?)" ;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryFilm, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setString(3, film.getReleaseDate());
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getRate().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(@NotNull Film film) throws ValidationException {
        String sqlQuery = "UPDATE films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, rate_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate().getId(),
                film.getId());
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
                    filmsRows.getString("release_date"),
                    filmsRows.getInt("duration"),
                    new RateMpa(filmsRows.getInt("rate_id"),
                            filmsRows.getString("rate_name")));

            log.info("Найден фильм: {} {}", film.getId(), film.getName());

            return Optional.of(film);
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            throw new NullPointerException("Фильм с id " + id + "  не найден");
            //return Optional.empty();
        }
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "SELECT * FROM films AS f JOIN rate_mpa AS rm ON f.rate_id = rm.rate_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilms(rs));
    }

    private Film makeFilms(ResultSet rs) throws SQLException {
        return new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("release_date"),
                rs.getInt("duration"),
                new RateMpa(rs.getInt("rate_id"),
                        rs.getString("rate_name")));
    }

    @Override
    public int generateId() {
        return 0;
    }
}
