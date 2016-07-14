package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<Role> ROLE_ROW_MAPPER = (rs, rowNum) -> Role.valueOf(rs.getString("role"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    private DataSourceTransactionManager transactionManager;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
        this.transactionManager = new DataSourceTransactionManager(dataSource);
    }

    @Override
    public User save(User user) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            MapSqlParameterSource map = new MapSqlParameterSource()
                    .addValue("id", user.getId())
                    .addValue("name", user.getName())
                    .addValue("email", user.getEmail())
                    .addValue("password", user.getPassword())
                    .addValue("registered", user.getRegistered())
                    .addValue("enabled", user.isEnabled())
                    .addValue("caloriesPerDay", user.getCaloriesPerDay());

            if (user.isNew()) {
                Number newKey = insertUser.executeAndReturnKey(map);
                user.setId(newKey.intValue());
                String sql = "INSERT INTO user_roles " +
                        "(role, user_id) VALUES (?, ?)";
                if (user.getRoles() != null)
                    for (Role role : user.getRoles()) {
                        jdbcTemplate.update(sql, new Object[]{role.toString(), user.getId()});
                    }

            } else {
                namedParameterJdbcTemplate.update(
                        "UPDATE users SET name=:name, email=:email, password=:password, " +
                                "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
                for (Role role : user.getRoles()) {
                    if (getRoles(user.getId()).contains(role)) {
                        MapSqlParameterSource map2 = new MapSqlParameterSource()
                                .addValue("role", role.toString())
                                .addValue("user_id", user.getId());
                        namedParameterJdbcTemplate.update("UPDATE user_roles SET role=:role WHERE user_id=:user_id", map2);
                    } else {
                        String sql = "INSERT INTO user_roles " +
                                "(role, user_id) VALUES (?, ?)";
                        jdbcTemplate.update(sql, new Object[]{role.toString(), user.getId()});
                    }
                }
            }

            transactionManager.commit(status);
        } catch (DataAccessException e1) {
            transactionManager.rollback(status);
            throw e1;
        } catch (Exception e2) {
            transactionManager.rollback(status);
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        User user = null;
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
            Set<Role> roles = getRoles(id);
            user = DataAccessUtils.singleResult(users);
            if (user != null)
                user.setRoles(roles);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
            user.setRoles(getRoles(user.getId()));
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return user;
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> users = Collections.EMPTY_LIST;
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
            for (User user : users) {
                user.setRoles(getRoles(user.getId()));
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
        return users;
    }

    public Set<Role> getRoles(int id) {
        return new HashSet<>(jdbcTemplate.query("SELECT role FROM user_roles WHERE user_id=?", ROLE_ROW_MAPPER, id));
    }
}
