$(function () {
    getlist(1);
});

function getlist(page) {
    var fileName = $('#fileName').val();
    var type = $('#type').val();
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    var result = $('#result').val();

    if (startDate != "" && endDate != "" && startDate >= endDate) {
        alert('后面的日期应该大于前面的日期！');
        return;
    }

    $.ajax({
        type: 'post',
        url: '/MessageStorage/record/getpage.action',
        data: {fileName: fileName, type: type, startDate: startDate, endDate: endDate, result: result, page: page},
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                list(page, data);
            }
        }
    });
}

function list(page, data) {
    var list = data.records;//后台返回的所有记录List
    var count = Math.floor((data.total + 9) / 10);//总页数
    var html = '';//要插入的动态代码
    //清空上次访问的数据
    $("#pagination").empty();
    $("#html").empty();

    var last = page - 1;
    if (page > 1) {
        $("#pagination").append("<li class=\"am-pagination-prev\">\n" +
            "        <a href=\"#\" class=\"\" onclick='getlist(" + last + ")'>上一页</a>\n" +
            "      </li>");
    }
    //跳转页数按钮
    $("#pagination").append(" <li class=\"am-pagination-select\" >\n" +
        "          <select id='select' onchange='selectFunc()'>");
    for (var i = 1; i <= count; i++) {
        if (i == page) {
            html = "  <option value=" + i + " class=\"\" selected=\"selected\" >" + i + "\n" +
                "\n" +
                "</option>"
        } else {
            html = "  <option value=" + i + " class=\"\">" + i + "\n" +
                "\n" +
                "</option>"
        }
        $("#select").append(html);
    }
    $("#pagination").append(" </select>\n" +
        "        </li>");

    if (count != 1 && page != count) {

        var next = page + 1;
        $("#pagination").append(" <li class=\"am-pagination-next \">\n" +
            "        <a href=\"#\" class=\"\" onclick='getlist(" + next + ")'>下一页</a>\n" +
            "      </li>");
    }
    for (var i = 0; i < list.length; i++) {
        html = "<tr>" +
            "<td>" + list[i].seq + "</td>" +
            "<td>" + list[i].type + "</td>" +
            "<td>" + list[i].delDate + "</td>" +
            "<td>" + list[i].result + "</td>" +
            "<td>" +
            "<div class=\"tpl-table-black-operation\">\n" +
            "<a href=\"javascript:;\" onclick= Detail('" + list[i].seq + "')>" +
            "<i class=\"am-icon-hand-pointer-o\"></i> 查看\n" +
            "</a>\n" +

            "</div>\n" +
            "</td></tr>";
        $("#html").append(html);

    }

}

function selectFunc() {
    var page = $('#select').val();
    getlist(page);
}

function Detail(seq) {
    $.getJSON('/MessageStorage/record/getBySeq.action?seq=' + seq,
        function (data) {
            if (!data.success) {
                alert(data.msg);
                return;
            }
            var record = data.record;
            if (!data.rs) {
                //失败的记录处理
                $('#failMsg').text(record.failMsg);
                $('#failPath').text(record.fileName);
                $('#fail-modal').modal({
                    relatedTarget: this
                })
            } else {
                //成功的记录处理

            }

            $('#my-prompt').modal({
                relatedTarget: this,
                onConfirm: function () {
                    var typeVal = $('#edittype').val();
                    var nameVal = $('#editname').val();
                    var cronExpressionVal = $('#editcronExpression').val();
                    var filePathVal = $('#editfilePath').val();
                    var timer = {
                        type: typeVal,
                        name: nameVal,
                        cronExpression: cronExpressionVal,
                        filePath: filePathVal,
                        seq: seq
                    };
                    $.ajax({
                        url: '/MessageStorage/timer/edit.action',
                        type: 'POST',
                        data: JSON.stringify(timer),
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            alert(data.msg);
                            window.location.href = '/MessageStorage/timer/list.action';
                        }
                    });
                },
                onCancel: function () {
                    alert('取消编辑定时器信息!');
                }
            });
        });
}
