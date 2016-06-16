package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

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
    private UserMealRestController userMealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        userMealRestController = appCtx.getBean(UserMealRestController.class);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("currentId", LoggedUser.getId());

        String id = request.getParameter("id");

        if (id != null) {


            UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
            userMealRestController.update(userMeal);
            response.sendRedirect("meals");

        } else {

            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");

            List<UserMealWithExceed> meals;

            LocalTime fromLocalTime = fromTime.equals("") ? LocalTime.MIN : LocalTime.parse(fromTime);
            LocalTime toLocalTime = toTime.equals("") ? LocalTime.MAX : LocalTime.parse(toTime);

            LocalDateTime fromLocalDateTime = fromDate.equals("") ? LocalDateTime.MIN : LocalDate.parse(fromDate).atTime(fromLocalTime);
            LocalDateTime toLocalDateTime = toDate.equals("") ? LocalDateTime.MAX : LocalDate.parse(toDate).atTime(toLocalTime);

            if (fromDate.equals("") && toDate.equals(""))
                meals = userMealRestController.getByTime(fromLocalTime, toLocalTime);
            else
                meals = userMealRestController.getByDateTime(fromLocalDateTime, toLocalDateTime);

            LOG.info("getByDateTimeFilter: from: " + fromLocalDateTime + ", to: " + toLocalDateTime);
            request.setAttribute("mealList", meals);
            request.getRequestDispatcher("mealList.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("currentId", LoggedUser.getId());

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", userMealRestController.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            userMealRestController.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LoggedUser.getId(), LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    userMealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        } else if (action.equals("logout")) {
            LoggedUser.setId(0);
            response.sendRedirect("index.html");
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }


    @Override
    public void destroy() {
        super.destroy();
        if (appCtx != null)
            appCtx.close();
    }

}
