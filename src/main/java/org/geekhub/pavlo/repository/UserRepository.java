package org.geekhub.pavlo.repository;

import org.geekhub.pavlo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public UserRepository() {

    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void add(User user) {
        String sql = "" +
                "INSERT INTO users (name, password, role) " +
                "VALUES (:name, :password, :role)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("role", user.getRole());

        namedJdbcTemplate.update(sql, parameters);
    }

    public void update(User user) {
        String sql = "" +
                "UPDATE users SET" +
                "   name =:name, " +
                "   password = :password, " +
                "   role = :role " +
                "WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("role", user.getRole());

        namedJdbcTemplate.update(sql, parameters);
    }

    public List<User> getUsers() {
        String sql = "" +
                "SELECT id, name, role " +
                "FROM users " +
                "ORDER BY name";

        List<User> res = namedJdbcTemplate.query(sql, this::mapUser);

        return res;
    }

    public User getUser(int userId) {
        String sql = "" +
                "SELECT id, name, role " +
                "FROM users " +
                "WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userId);

        List<User> users = namedJdbcTemplate.query(sql, parameters, this::mapUser);

        if (users.isEmpty()) {
            throw new IllegalArgumentException("Can't find User id=" + userId);
        }

        return users.get(0);
    }

    public void deleteUser(int userId) {
        String sql = "" +
                "DELETE " +
                "FROM users " +
                "WHERE id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", userId);

        namedJdbcTemplate.update(sql, parameters);
    }

    private User mapUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        return user;
    }

}
