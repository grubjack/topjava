<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>User list</h3>
    <a href="users?action=create">Add User</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Roles</th>
            <th>Active</th>
            <th>Registered</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${user}" var="users">
            <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.roles}</td>
                <td>${user.enabled}</td>
                <td>${user.registered}</td>
                <td><a href="users?action=update&id=${user.id}">Update</a></td>
                <td><a href="users?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
