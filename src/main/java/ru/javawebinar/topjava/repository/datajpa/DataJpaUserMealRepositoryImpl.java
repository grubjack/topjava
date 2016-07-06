package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {
    private static final Sort SORT_DATE = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private ProxyUserMealRepository proxy;

    @Autowired
    private ProxyUserRepository proxyUser;


    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }

        userMeal.setUser(proxyUser.getOne(userId));
        return proxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (get(id, userId) == null)
            return false;
        else
            proxy.delete(id);
        return true;
    }

    @Override
    public UserMeal get(int id, int userId) {
        UserMeal userMeal = proxy.findOne(id);
        return userMeal.getUser().getId() != userId ? null : userMeal;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.findAll(userId, SORT_DATE);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.getBetween(startDate, endDate, userId);
    }
}
