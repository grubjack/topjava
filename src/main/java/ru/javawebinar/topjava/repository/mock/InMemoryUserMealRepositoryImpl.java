package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

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
        UserMealsUtil.MEAL_LIST.forEach(userMeal -> save(userMeal.getUserId(), userMeal));
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        LOG.info("save " + userMeal);
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        } else if (userMeal.getUserId() != userId)
            return null;

        return repository.put(userMeal.getId(), userMeal);
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("delete " + id);
        return repository.get(id).getUserId() == userId && repository.remove(id) != null;

    }

    @Override
    public UserMeal get(int userId, int id) {
        LOG.info("get " + id);
        return repository.get(id).getUserId() != userId ? null : repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        LOG.info("getAll");
        return repository.values().stream().filter(userMeal -> userMeal.getUserId() == userId).sorted(Comparator.comparing(UserMeal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getByTime(int userId, LocalTime from, LocalTime to) {
        LOG.info("getByTime: from: " + from + ", to: " + to);
        return getAll(userId).stream().filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), from, to)).collect(Collectors.toList());
    }

    @Override
    public List<UserMeal> getByDateTime(int userId, LocalDateTime from, LocalDateTime to) {
        LOG.info("getByDateTime: from: " + from + ", to: " + to);
        return getAll(userId).stream().filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime(), from, to)).collect(Collectors.toList());
    }
}

