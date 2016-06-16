package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    UserMeal save(int userId, UserMeal userMeal);

    boolean delete(int userId, int id);

    UserMeal get(int userId, int id);

    Collection<UserMeal> getAll(int userId);

    List<UserMeal> getByTime(int userId, LocalTime from, LocalTime to);

    List<UserMeal> getByDateTime(int userId, LocalDateTime from, LocalDateTime to);
}
