package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public List<UserMeal> getByDate(LocalDate from, LocalDate to) {
        return repository.getByDate(from, to);
    }

    @Override
    public List<UserMeal> getByTime(LocalTime from, LocalTime to) {
        return repository.getByTime(from, to);
    }

    @Override
    public List<UserMeal> getByDateTime(LocalDateTime from, LocalDateTime to) {
        return repository.getByDateTime(from, to);
    }

    @Override
    public UserMeal save(UserMeal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public UserMeal get(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<UserMeal> getAll() {
        return (List<UserMeal>) repository.getAll();
    }

    @Override
    public User getUser(int id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.getUser(id), id);
    }

    @Override
    public void update(UserMeal meal) {
        repository.save(meal);
    }
}
