package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by user on 04.07.2016.
 */
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Override
    @Transactional
    @Modifying
    void delete(Integer id);

    @Override
    @Transactional
    UserMeal save(UserMeal user);

    @Query("SELECT m FROM UserMeal m LEFT JOIN FETCH m.user WHERE m.id=?1")
    UserMeal findOne(Integer id);

    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId")
    List<UserMeal> findAll(@Param("userId") int userId, Sort sort);

    @Query("SELECT m FROM UserMeal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

}
