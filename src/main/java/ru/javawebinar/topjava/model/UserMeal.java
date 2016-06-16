package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal extends BaseEntity {
    private final LocalDateTime dateTime;
    private final String description;
    private final int calories;
    private final int userId;

    public UserMeal(int userId, LocalDateTime dateTime, String description, int calories) {
        this(null, userId, dateTime, description, calories);
    }

    public UserMeal(Integer id, int userId, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", userId=" + userId +
                '}';
    }
}
