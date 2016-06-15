package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */

public interface UserMealService {
    UserMeal save(UserMeal meal);

    void delete(int id) throws NotFoundException;

    UserMeal get(int id) throws NotFoundException;

    List<UserMeal> getAll();

    User getUser(int id) throws NotFoundException;

    List<UserMeal> getByDate(LocalDate from, LocalDate to);

    List<UserMeal> getByTime(LocalTime from, LocalTime to);

    List<UserMeal> getByDateTime(LocalDateTime from, LocalDateTime to);

    void update(UserMeal meal);
}
