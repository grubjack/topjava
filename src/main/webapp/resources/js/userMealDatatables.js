var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;


$('.date-picker').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});

$('.time-picker').datetimepicker({
    datepicker: false,
    format: 'H:i'
});

$('.date-time-picker').datetimepicker({
    format: 'Y-m-d H:m:s'
});

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        var d = new Date(data);
                        var fd = d.getFullYear() + "-" + ("00" + (d.getMonth() + 1)).slice(-2) + "-" +
                            ("00" + d.getDate()).slice(-2) + " " +
                            ("00" + d.getHours()).slice(-2) + ":" +
                            ("00" + d.getMinutes()).slice(-2);
                        return fd;
                    }
                    return data;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        },
        "initComplete": makeEditable
    });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });
});