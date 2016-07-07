package ru.javawebinar.topjava.repository.jdbc.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserMealRepository;

import javax.sql.DataSource;

/**
 * Created by user on 07.07.2016.
 */
@Profile(value = "postgres")
@Repository
public class PostgresJdbcUserMealRepositoryImpl extends JdbcUserMealRepository {

    @Autowired
    public PostgresJdbcUserMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        rowMapper = BeanPropertyRowMapper.newInstance(UserMeal.class);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("date_time", userMeal.getDateTime())
                .addValue("user_id", userId);
        return saveUserMeal(userMeal, map);
    }
}