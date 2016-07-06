package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        System.setProperty("spring.profiles.active", Profiles.ACTIVE_DB + "," + Profiles.DATAJPA);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            for (String bean : appCtx.getBeanDefinitionNames()) {
                System.out.println(bean);
            }
//    AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            System.out.println(adminUserController.create(UserTestData.USER));
//            System.out.println();

//            UserMealRestController mealController = appCtx.getBean(UserMealRestController.class);
//            List<UserMealWithExceed> filteredMealsWithExceeded =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
