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
import ru.yandex.practicum.filmorate.model.*;
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
               "values (?, ?, ?, ?, ?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();           // вернуть id, сгенерированный в БД
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryFilm, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setString(3, film.getReleaseDate());
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());

            return stmt;
        }, keyHolder);

        film.setId(keyHolder.getKey().intValue());
        if (film.getGenres() != null) {
            saveGenres(film);
        }
    }
    private void saveGenres(Film film) {
        String sqlQueryGenre = "insert into genres_films(film_id, genre_id) " +
                "values (?, ?) ";
        for (Genre genre : film.getGenres()) {
             jdbcTemplate.update(sqlQueryGenre,
                    film.getId(),
                    genre.getId());
        }
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
                film.getMpa().getId(),
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
                    filmsRows.getInt("rate_id"));
// здесь надо дописать приклеивание жанра к фильму
            log.info("Найден фильм: {} {}", film.getId(), film.getName());

            return Optional.of(film);
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            throw new NullPointerException("Фильм с id " + id + "  не найден");

        }
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "SELECT * " +
                "FROM films AS f JOIN rate_mpa AS rm ON f.rate_id = rm.rate_id " +
                "ORDER BY f.film_id ASC" ;
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilms(rs));
    }

    private Film makeFilms(ResultSet rs) throws SQLException {
        String sql = "SELECT * " +
                "FROM films AS f JOIN rate_mpa AS rm ON f.rate_id = rm.rate_id ";
        return new Film(
                rs.getInt("film_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("release_date"),
                rs.getInt("duration"),
                makeMpa(rs.getInt("rate_id")),
                makeGenresCollection());
    }

    private Mpa makeMpa(int rate_id) {
        return new Mpa(rate_id);
    }

    private Collection<Genre> makeGenresCollection() {
        String sql = "SELECT * " +
                "FROM films AS f JOIN genres_films AS gf ON f.film_id = gf.film_id " +
                "ORDER BY gf.genre_id";

        String sql2 = "SELECT * " +
                "FROM films " +
                "WHERE EXISTS " +
                "(SELECT gf.genre_id " +
                "FROM films AS f JOIN genres_films AS gf ON f.film_id = gf.film_id " +
                "ORDER BY gf.genre_id)";


        return jdbcTemplate.query(sql2, (rs, rowNum) -> makeGenre(rs));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        //  здесь надо проверять возвращается налл или что возвращается...
        return new Genre(
                rs.getString("genre_name"),
                rs.getInt("genre_id")
                );
    }

    @Override
    public int generateId() {
        return 0;
    }
}
