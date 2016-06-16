package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.UserMealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    private static int id = 0;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
