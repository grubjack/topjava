package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * Created by user on 13.07.2016.
 */

@Controller
public class UserMealController extends AbstractUserMealRestController {


    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealList(Model model) {
        model.addAttribute("mealList", super.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals/edit", method = RequestMethod.GET)
    public String updateMeal(@RequestParam(value = "id", required = false) Integer id, Model model) {
        UserMeal userMeal;
        if (id != null) {
            userMeal = super.get(id);
        } else {
            userMeal = new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000);
            model.addAttribute("action", "create");
        }
        model.addAttribute("meal", userMeal);
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
            super.create(userMeal);
        } else {
            super.update(userMeal, Integer.parseInt(request.getParameter("id")));
        }
        return "redirect:/meals";
    }


    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
    public String deleteMeal(@RequestParam(value = "id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }


    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String filterMeal(@RequestParam(value = "startDate") String startDate,
                             @RequestParam(value = "endDate") String endDate,
                             @RequestParam(value = "startTime") String startTime,
                             @RequestParam(value = "endTime") String endTime,
                             Model model) {
        model.addAttribute("mealList", super.getBetween(
                TimeUtil.parseLocalDate(startDate),
                TimeUtil.parseLocalTime(startTime),
                TimeUtil.parseLocalDate(endDate),
                TimeUtil.parseLocalTime(endTime)));
        return "mealList";
    }
}

