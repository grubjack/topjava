package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.jdbc.JdbcUserMealRepository;
import ru.javawebinar.topjava.repository.jdbc.LocalDateTimePersistenceConverter;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by user on 07.07.2016.
 */

@Profile(value = "hsqldb")
@Repository
public class HsqldbJdbcUserMealRepositoryImpl extends JdbcUserMealRepository {
    private static final LocalDateTimePersistenceConverter CONVERTER = new LocalDateTimePersistenceConverter();

    @Autowired
    public HsqldbJdbcUserMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
        rowMapper = (rs, rowNum) -> {
            UserMeal userMeal = new UserMeal();
            userMeal.setId(rs.getInt("id"));
            User user = new User();
            user.setId(rs.getInt("user_id"));
            userMeal.setUser(user);
            userMeal.setDateTime(CONVERTER.convertToEntityAttribute(rs.getTimestamp("date_time")));
            userMeal.setDescription(rs.getString("description"));
            userMeal.setCalories(rs.getInt("calories"));
            return userMeal;
        };
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("date_time", CONVERTER.convertToDatabaseColumn(userMeal.getDateTime()))
                .addValue("user_id", userId);
        return saveUserMeal(userMeal, map);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC", rowMapper
                , userId, CONVERTER.convertToDatabaseColumn(startDate), CONVERTER.convertToDatabaseColumn(endDate));
    }
}