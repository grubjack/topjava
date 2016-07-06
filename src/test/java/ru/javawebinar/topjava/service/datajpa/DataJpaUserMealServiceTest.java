package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

/**
 * Created by user on 06.07.2016.
 */

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserMealServiceTest extends UserMealServiceTest {
}
