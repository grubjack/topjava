package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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

    void update(UserMeal meal);
}
