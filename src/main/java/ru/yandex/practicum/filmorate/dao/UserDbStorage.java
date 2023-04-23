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
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        String sqlQueryUser = "insert into users(email, login, user_name, birthday) " +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQueryUser, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getBirthday());
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
                    usersRows.getString("email"),
                    usersRows.getString("login"),
                    usersRows.getString("user_name"),
                    usersRows.getString("birthday"));

            log.info("Найден пользователь: {} {}", user.getId(), user.getName());

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            throw new NullPointerException("Пользователь с id " + id + " не найден");
        }
    }

    @Override
    public void update(User user) {
        String sqlQuery = "UPDATE users set " +
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
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("user_name"),
                rs.getString("birthday"));
    }

    public Collection<User> getFriends(int id) {
        String sqlQuery = "SELECT u.* " +
                "FROM users AS u JOIN friends AS f on u.user_id = f.friend_to " +
                "WHERE f_user_id = " + id +
                " ORDER BY f_user_id ASC";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUsers(rs));
    }

    public Collection<User> getCommonFriends(int id1, int id2) {
        String sqlQuery =
                "SELECT u.* " +
                        "FROM users AS u JOIN friends AS f on u.user_id = f.friend_to " +
                        "WHERE f_user_id = " + id2 +
                        " INTERSECT " +
                        "SELECT u.* " +
                        "FROM users AS u JOIN friends AS f on u.user_id = f.friend_to " +
                        "WHERE f_user_id = " + id1;

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUsers(rs));
    }

    public void addFriend(int idUser, int idFriend) {
        String sqlQuery = "INSERT INTO friends " +
                "(f_user_id, friend_to) " +
                "values (?, ?) ";
        jdbcTemplate.update(sqlQuery,
                idUser,
                idFriend);
    }

    public void deleteFromFriends(int idUser, int idFriend) {
        String sqlQuery = "DELETE FROM friends WHERE f_user_id = ? " +
                "AND friend_to = ? ";
        jdbcTemplate.update(sqlQuery,
                idUser,
                idFriend);
    }


    @Override
    public int generateId() {
        return 0;
    }

}
