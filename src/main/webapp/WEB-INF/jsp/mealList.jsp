<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="meals.title"/></h3>
            <form class="form-horizontal" method="post" id="filterForm">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="startDate">From Date:</label>
                    <div class="col-sm-2">
                        <input type="date" class="form-control" id="startDate" name="startDate"/>
                    </div>
                    <label class="control-label col-sm-2" for="endDate">To Date:</label>
                    <div class="col-sm-2">
                        <input type="date" class="form-control" id="endDate" name="endDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="startTime">From Time:</label>
                    <div class="col-sm-2">
                        <input type="time" class="form-control" id="startTime" name="startTime"/>
                    </div>

                    <label class="control-label col-sm-2" for="endTime">To Time:</label>
                    <div class="col-sm-2">
                        <input type="time" class="form-control" id="endTime" name="endTime"/>
                    </div>
                </div>
                <button class="btn btn-sm btn-info center-block" type="submit"><fmt:message
                        key="meals.filter"/></button>
            </form>
            <hr>
            <div class="view-box">
                <div class="view-box">
                    <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>

                    <table class="table table-striped display" id="datatable">
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
                            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                            <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                                <td>
                                        ${fn:formatDateTime(meal.dateTime)}
                                </td>
                                <td>${meal.description}</td>
                                <td>${meal.calories}</td>
                                <td><a class="btn btn-xs btn-primary update">Update</a></td>
                                <td><a class="btn btn-xs btn-danger delete">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>


<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="date" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="date" name="dateTime"
                                   placeholder="Date">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description"
                                   placeholder="Description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Calories</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="calories" name="calories"
                                   placeholder="2000">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                            <button type="reset" class="btn btn-primary">Reset</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</body>

<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">

    var ajaxUrl = 'ajax/profile/meals/';
    var datatableApi;

    // $(document).ready(function () {
    $(function () {
        datatableApi = $('#datatable').DataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "sDefaultContent": "Update",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "Delete",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "asc"
                ]
            ]
        });
        makeEditable();
    });
</script>
</html>
