package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */

public interface UserMealService {
    UserMeal save(int userId, UserMeal meal);

    void delete(int userId, int id) throws NotFoundException;

    UserMeal get(int userId, int id) throws NotFoundException;

    List<UserMealWithExceed> getAll(int userId);

    List<UserMealWithExceed> getByTime(int userId, LocalTime from, LocalTime to);

    List<UserMealWithExceed> getByDateTime(int userId, LocalDateTime from, LocalDateTime to);
}
