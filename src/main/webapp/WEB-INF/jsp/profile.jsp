<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <spring:message code="title.profile" var="profileTitle"/>
            <spring:message code="title.register" var="registerTitle"/>
            <h2>${register ? registerTitle : userTo.name.concat(' ').concat(profileTitle)}</h2>

            <div class="view-box">
                <form:form modelAttribute="userTo" class="form-horizontal" method="post"
                           action="${register ? registerTitle : profileTitle}" charset="utf-8"
                           accept-charset="UTF-8">
                    <spring:message code="table.name" var="titleName"/>
                    <custom:inputField label="${titleName}" name="name"/>
                    <custom:inputField label="Email" name="email"/>
                    <spring:message code="table.password" var="titlePassword"/>
                    <custom:inputField label="${titlePassword}" name="password" inputType="password"/>
                    <spring:message code="table.calperday" var="titleCalories"/>
                    <custom:inputField label="${titleCalories}" name="caloriesPerDay" inputType="number"/>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-10">
                            <spring:message code="button.add" var="buttonAdd"/>
                            <spring:message code="button.update" var="buttonUpdate"/>
                            <button type="submit"
                                    class="btn btn-primary">${register ? buttonAdd : buttonUpdate}</button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
