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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    private void dropGenres(Film film) {
        String sqlQueryGenre = "DELETE FROM genres_films WHERE film_id = ? ";
        jdbcTemplate.update(sqlQueryGenre,
                film.getId());
    }

    @Override
    public void update(@NotNull Film film) throws ValidationException {
        String sqlQuery = "UPDATE films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, rate_id = ? " +
                "WHERE film_id = ? ";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        dropGenres(film);
        saveFilmGenres(film, film.getId());
    }

    private void saveFilmGenres(Film film, int filmId) {
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        String sqlQuery = "insert into genres_films (film_id, genre_id) " +
                "values (?, ?)";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlQuery,
                    filmId,
                    genre.getId());
        }
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
                    filmsRows.getInt("rate_id"),
                    makeGenresCollection(id)
            );
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
                "ORDER BY f.film_id ASC";
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
                makeGenresCollection(rs.getInt("film_id")));
    }

    private Mpa makeMpa(int rate_id) {
        return new Mpa(rate_id);
    }

    private Collection<Genre> makeGenresCollection(int film_id) {
        String sql = "SELECT * " +
                "FROM genres_films AS gf JOIN genre AS g ON g.genre_id = gf.genre_id " +
                "WHERE gf.film_id = " + film_id +
                "ORDER BY gf.genre_id ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getString("genre_name"),
                rs.getInt("genre_id")
        );
    }

    public void addLikes(int id, int idOfUser) {
        String sqlQueryLike = "insert into fan_list(film_id_f, user_id) " +
                "values (?, ?) ";
        jdbcTemplate.update(sqlQueryLike,
                id,
                idOfUser);
    }

    public void deleteLikes(int id, int idOfUser) {
        String sqlQueryDeleteLike = "DELETE FROM fan_list WHERE film_id_f = ?" +
                " AND user_id = ? ";
        jdbcTemplate.update(sqlQueryDeleteLike,
                id,
                idOfUser);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        String sqlQuery = "SELECT *, COUNT(film_id_f) " +
                "FROM fan_list AS fl " +
                "JOIN films AS f ON f.film_id = fl.film_id_f " +
                "GROUP BY film_id_f " +
                "ORDER BY COUNT(film_id_f) DESC " +
                "LIMIT " + count;
        Collection<Film> popularFilms = new ArrayList<>();
        popularFilms = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilms(rs));
        if (popularFilms.isEmpty()) {
            return new ArrayList<>(getFilms()); // если лайков нет, то возвращаем все фильмы
        }
        return popularFilms;
    }


    @Override
    public int generateId() {
        return 0;
    }
}
