<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
LoggedUser: <b>id=${userId}</b>
<br>
<a href="meals?action=logout">Logout</a>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list</h3>
    <form method="post" action="meals">
        <table border="0" cellpadding="8" cellspacing="0">
            <tr>
                <td>From Date:</td>
                <td><input type="date" name="fromDate"></td>
                <td>To Date:</td>
                <td><input type="date" name="toDate"></td>
            </tr>
            <tr>
                <td>From Time:</td>
                <td><input type="time" name="fromTime"></td>
                <td>To Time:</td>
                <td><input type="time" name="toTime"></td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <button type="submit">Filter</button>
                </td>
            </tr>
        </table>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <br>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>