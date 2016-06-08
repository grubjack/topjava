<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1px" cellpadding="1" cellspacing="1">
    <thead>
    <tr>
        <th width="20%">date</th>
        <th width="20%">description</th>
        <th width="20%">calories</th>
        <th width="20%">isExceed</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:<c:out value="${meal.exceed ? 'red' : 'green'}"/>;">
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
