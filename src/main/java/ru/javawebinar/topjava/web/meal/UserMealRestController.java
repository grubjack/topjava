package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);


    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll");
        return service.getAll(LoggedUser.getId());
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(LoggedUser.getId(), id);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(LoggedUser.getId(), id);
    }

    public void update(UserMeal meal) {
        LOG.info("update " + meal);
        service.save(LoggedUser.getId(), meal);
    }

    public List<UserMealWithExceed> getByDateTime(LocalDateTime start, LocalDateTime end) {
        LOG.info("getByDateTime");
        return service.getByDateTime(LoggedUser.getId(), start, end);
    }

    public List<UserMealWithExceed> getByTime(LocalTime start, LocalTime end) {
        LOG.info("getByTime");
        return service.getByTime(LoggedUser.getId(), start, end);
    }
}
