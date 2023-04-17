package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@Primary
@Slf4j
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String sqlQueryUser = "insert into films(user_name, birthday, login, email) " +
                "values (?, ?, ?, ?)" ;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryUser, new String[]{"user_id"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getBirthday());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getEmail());
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) {
    SqlRowSet usersRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE user_id = ? ", id);
        if (usersRows.next()) {
            User user = new User(
                    usersRows.getInt("user_id"),
                    usersRows.getString("user_name"),
                    usersRows.getString("birthday"),
                    usersRows.getString("login"),
                    usersRows.getString("email"));

            log.info("Найден пользователь: {} {}", user.getId(), user.getName());

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    @Override
    public void update(User user) {
        String sqlQuery = "UPDATE user set " +
                "user_name = ?, birthday = ?, login = ?, email = ? " +
                "WHERE user_id = ?";

        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getBirthday(),
                user.getLogin(),
                user.getEmail(),
                user.getId());
    }

    @Override
    public Collection<User> getUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUsers(rs));
    }

    private User makeUsers(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("user_name"),
                rs.getString("birthday"),
                rs.getString("login"),
                rs.getString("email"));
    }

    @Override
    public int generateId() {
        return 0;
    }
}
