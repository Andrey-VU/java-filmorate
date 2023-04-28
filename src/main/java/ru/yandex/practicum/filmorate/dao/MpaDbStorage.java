package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@Slf4j
public class MpaDbStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Mpa> getMpaById(int id) {
        SqlRowSet ratesRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM rate_mpa " +
                "WHERE rate_id = ?", id);
        if (ratesRows.next()) {
            Mpa rateMpa = new Mpa(
                    ratesRows.getString("rate_name"),
                    ratesRows.getInt("rate_id"));

            log.info("Найден рейтинг MPA: {} {}", rateMpa.getId(), rateMpa.getName());

            return Optional.of(rateMpa);
        } else {
            log.info("Рейтинг MPA с идентификатором {} не найден.", id);
            throw new NullPointerException("Рейтинг MPA с id " + id + "  не найден");
        }
    }

    public Collection<Mpa> getRates() {    // получение списка всех жанров
        String sql = "SELECT * FROM rate_mpa " +
                "ORDER BY rate_id ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeRates(rs));
    }

    private Mpa makeRates(ResultSet rs) throws SQLException {
        return new Mpa(
                rs.getString("rate_name"),
                rs.getInt("rate_id"));
    }

}
