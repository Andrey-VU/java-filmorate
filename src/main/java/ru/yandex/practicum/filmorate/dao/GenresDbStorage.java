package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@Slf4j
public class GenresDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenresDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Genre> getGenreById(int id) {
        SqlRowSet genresRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM genre " +
                "WHERE genre_id = ? ", id);
        if (genresRows.next()) {
            Genre genre = new Genre(
                    genresRows.getString("genre_name"),
                    genresRows.getInt("genre_id")
            );
            log.info("Найден жанр: {} {}", genre.getId(), genre.getName());
            return Optional.of(genre);
        } else {
            log.info("Жанр с идентификатором {} не найден.", id);
            throw new NullPointerException("Жанр с id " + id + "  не найден");
        }
    }

    public Collection<Genre> getGenres() {    // получение списка всех жанров
        String sql = "SELECT * FROM genre " +
                "ORDER BY genre_id ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getString("genre_name"),
                rs.getInt("genre_id")
        );
    }

    public void saveGenres(Film film) {
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        String sqlQueryGenre = "insert into genres_films(film_id, genre_id) " +
                "values (?, ?) ";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlQueryGenre,
                    film.getId(),
                    genre.getId());
        }
    }

    public void dropGenres(Film film) {
        String sqlQueryGenre = "DELETE FROM genres_films WHERE film_id = ? ";
        jdbcTemplate.update(sqlQueryGenre,
                film.getId());
    }
}

