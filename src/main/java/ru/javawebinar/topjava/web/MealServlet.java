package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private UserMealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//        repository = new InMemoryUserMealRepositoryImpl();
            repository = appCtx.getBean(UserMealRepository.class);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


        String id = request.getParameter("id");

        if (id != null) {


            UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
            repository.save(userMeal);
            response.sendRedirect("meals");

        } else {

            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");

            List<UserMeal> meals;

            LocalTime fromLocalTime = fromTime.equals("") ? LocalTime.MIN : LocalTime.parse(fromTime);
            LocalTime toLocalTime = toTime.equals("") ? LocalTime.MAX : LocalTime.parse(toTime);

            LocalDateTime fromLocalDateTime = fromDate.equals("") ? LocalDateTime.MIN : LocalDate.parse(fromDate).atTime(fromLocalTime);
            LocalDateTime toLocalDateTime = toDate.equals("") ? LocalDateTime.MAX : LocalDate.parse(toDate).atTime(toLocalTime);

            if (fromDate.equals("") && toDate.equals(""))
                meals = repository.getByTime(fromLocalTime, toLocalTime);
            else
                meals = repository.getByDateTime(fromLocalDateTime, toLocalDateTime);

            LOG.info("getByDateTimeFilter: from: " + fromLocalDateTime + ", to: " + toLocalDateTime);
            request.setAttribute("mealList", UserMealsUtil.getWithExceeded(meals, UserMealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.getRequestDispatcher("mealList.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    UserMealsUtil.getWithExceeded(repository.getAll(), UserMealsUtil.DEFAULT_CALORIES_PER_DAY));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            repository.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    repository.get(getId(request));
            request.setAttribute("meal", meal);

        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
