package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.USER_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by user on 23.06.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }


    @Test(expected = NotFoundException.class)
    public void get() throws Exception {
        service.get(USER_MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(ADMIN_MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void update() throws Exception {
        service.update(service.getAll(ADMIN_ID).iterator().next(), USER_ID);
    }

    @Test
    public void updateMeal() throws Exception {
        Assert.assertNotNull(service.update(service.getAll(ADMIN_ID).iterator().next(), ADMIN_ID));
    }

}