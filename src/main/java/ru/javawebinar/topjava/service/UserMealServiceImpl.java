package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);

    @Autowired
    private UserMealRepository repository;

    @Override
    public List<UserMealWithExceed> getByTime(int userId, LocalTime from, LocalTime to) {
        LOG.info("getByTime");
        return UserMealsUtil.getFilteredWithExceeded(repository.getByTime(userId, from, to), from, to, LoggedUser.getCaloriesPerDay());
    }

    @Override
    public List<UserMealWithExceed> getByDateTime(int userId, LocalDateTime from, LocalDateTime to) {
        LOG.info("getByDateTime");
        return UserMealsUtil.getFilteredWithExceeded(repository.getByDateTime(userId, from, to), from.toLocalTime(), to.toLocalTime(), LoggedUser.getCaloriesPerDay());
    }

    @Override
    public List<UserMealWithExceed> getAll(int userId) {
        LOG.info("getAll");
        return UserMealsUtil.getFilteredWithExceeded(repository.getAll(userId), LocalTime.MIN, LocalTime.MAX, LoggedUser.getCaloriesPerDay());
    }

    @Override
    public UserMeal save(int userId, UserMeal meal) {
        LOG.info("save");
        return ExceptionUtil.checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        LOG.info("delete");
        ExceptionUtil.checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public UserMeal get(int userId, int id) throws NotFoundException {
        LOG.info("get");
        return ExceptionUtil.checkNotFoundWithId(repository.get(userId, id), id);
    }
}
