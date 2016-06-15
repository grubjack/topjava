package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

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

    public List<UserMeal> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public UserMeal get(int id) {
        if (LoggedUser.id() != service.get(id).getUser().getId())
            throw new NotFoundException("access denied");

        LOG.info("get " + id);
        return service.get(id);
    }

    public UserMeal create(UserMeal meal) {
        LOG.info("create " + meal);
        meal.setId(null);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(UserMeal meal, int id) {
        LOG.info("update " + meal);
        if (LoggedUser.id() != meal.getUser().getId())
            throw new NotFoundException("access denied");

        meal.setId(id);
        service.update(meal);
    }
}
