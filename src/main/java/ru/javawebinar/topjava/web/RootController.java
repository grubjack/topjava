package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private UserMealService serviceUserMeal;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
        model.addAttribute("mealList", UserMealsUtil.getWithExceeded(serviceUserMeal.getAll(AuthorizedUser.id), AuthorizedUser.getCaloriesPerDay()));
        return "mealList";
    }


    @RequestMapping(value = "/meals/add", method = RequestMethod.GET)
    public String addMeal(Model model) {
        UserMeal userMeal = new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000);
        model.addAttribute("meal", userMeal);
        model.addAttribute("action", "create");
        return "mealEdit";
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String updateMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        final UserMeal userMeal = new UserMeal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            userMeal.setId(null);
            serviceUserMeal.save(userMeal, AuthorizedUser.id);
        } else {
            userMeal.setId(Integer.parseInt(request.getParameter("id")));
            serviceUserMeal.update(userMeal, AuthorizedUser.id);
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/update", method = RequestMethod.GET)
    public String updateMeal(@RequestParam(value = "id") int id, Model model) {
        UserMeal userMeal = serviceUserMeal.get(id, AuthorizedUser.id);
        model.addAttribute("meal", userMeal);
        return "mealEdit";
    }


    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
    public String deleteMeal(@RequestParam(value = "id") int id) {
        serviceUserMeal.delete(id, AuthorizedUser.id);
        return "redirect:/meals";
    }


    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String filterMeal(@RequestParam(value = "startDate") LocalDate startDate,
                             @RequestParam(value = "endDate") LocalDate endDate,
                             @RequestParam(value = "startTime") LocalTime startTime,
                             @RequestParam(value = "endTime") LocalTime endTime,
                             Model model) {
        model.addAttribute("mealList", UserMealsUtil.getFilteredWithExceeded(serviceUserMeal.getBetweenDates(
                startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, AuthorizedUser.id),
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, AuthorizedUser.getCaloriesPerDay()));
        return "mealList";
    }
}
