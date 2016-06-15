package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save " + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public void delete(int id) {
        LOG.info("delete " + id);
        repository.remove(id);
    }

    @Override
    public UserMeal get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        LOG.info("getAll");
        return repository.values().stream().sorted(Comparator.comparing(UserMeal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getByDate(LocalDate from, LocalDate to) {
        LOG.info("getByDate: from: " + from + ", to: " + to);
        return getAll().stream().filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalDate(), from, to)).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getByTime(LocalTime from, LocalTime to) {
        LOG.info("getByTime: from: " + from + ", to: " + to);
        return getAll().stream().filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), from, to)).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getByDateTime(LocalDateTime from, LocalDateTime to) {
        LOG.info("getByDateTime: from: " + from + ", to: " + to);
        return getAll().stream().filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime(), from, to)).collect(Collectors.toList());
    }

    @Override
    public User getUser(int id) {
        LOG.info("getUserByMealId " + id);
        return get(id).getUser();
    }
}

