$(function () {
    getlist(1);
});

function getlist(page) {
    var name = $('#timername').val();
    var type = $('#timertype').val();

    $.AMUI.progress.start();
    $.ajax({
        type: 'post',
        url: '/MessageStorage/timer/getpage.action',
        data: {name: name, type: type, page: page},
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                list(page, data);
            }
        }
    });
    $.AMUI.progress.done();
}

//点击编辑调用
function edittimer(seq, status) {
    if (status == "0") {
        alert("无法修改正在运行定时器！");
        return;
    }

    $.getJSON('/MessageStorage/timer/getBySeq.action?seq=' + seq,
        function (data) {
            if (!data.success) {
                alert(data.msg);
                window.location.href = '/MessageStorage/timer/list.action';
            }
            $("#edittype").find("option[value='" + data.timer.type + "']").attr("selected", true);
            $("#edittype").trigger('changed.selected.amui');
            $('#editname').val(data.timer.name);
            $('#editcronExpression').val(data.timer.cronExpression);
            $('#editfilePath').val(data.timer.filePath);
            $('#my-prompt2').modal({
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
                    $.AMUI.progress.start();
                    $.ajax({
                        url: '/MessageStorage/timer/edit.action',
                        type: 'POST',
                        data: JSON.stringify(timer),
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            if(data.suc){
                                alert(data.msg);
                                window.location.reload();
                            }else{
                                nextF(data);
                            }
                        }
                    });
                    $.AMUI.progress.done();
                },
                onCancel: function () {
                    alert('取消编辑定时器信息!');
                }
            });
        });

}

//点击查看调用
function viewtimer(seq) {
    $.getJSON('/MessageStorage/timer/getBySeq.action?seq=' + seq,
        function (data) {
            if (!data.success) {
                alert(data.msg);
                window.location.href = '/MessageStorage/timer/list.action';
            }
            $("#viewtype").find("option[value='" + data.timer.type + "']").attr("selected", true);
            $("#viewtype").trigger('changed.selected.amui');
            $('#viewname').val(data.timer.name);
            $('#viewExpression').val(data.timer.cronExpression);
            $('#viewfilePath').val(data.timer.filePath);
            $('#my-prompt3').modal({
                relatedTarget: this,
                onConfirm: function () {

                },
                onCancel: function () {

                }
            });
        });

}

function change(seq, status) {
    $.ajax({
        url: '/MessageStorage/timer/changeStatus.action?seq=' + seq + '&status=' + status,
        type: 'GET',
        success: function (data) {
            alert(data.msg);
            window.location.reload();
        }
    });
}

function list(page, data) {
    var list = data.timers;//后台返回的所有定时器List
    var count = Math.floor((data.total + 8) / 9);//总页数
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
        var status = "<a class='am-btn am-btn-default' href=\"javascript:;\" onclick=change('" + list[i].seq + "','" + 0 + "')>" + "未执行</a>&nbsp;&nbsp;&nbsp;";
        if (list[i].status == "0") {
            status = "<a class='am-btn am-btn-success' href=\"javascript:;\" onclick=change('" + list[i].seq + "','" + 1 + "')>" + "执行中</a>&nbsp;&nbsp;&nbsp;";
        }

        if (i == 3 || i == 6) {
            html = "</div></div><div class=\"row-content am-cf\"><div class=\"row  am-cf\">";
            $("#html").append(html);
        }

        html = "<div class=\"am-u-sm-12 am-u-md-6 am-u-lg-4 am-u-end\">" +
            "<div class=\"widget widget-purple am-cf\">" +
            "<div style=\"width: 50%\">" + status + getType(list[i].type) +
            "</div>" +
            "<div class=\"widget-statistic-body\">" +
            "<div class=\"widget-statistic-value\">" +
            "<small>" + list[i].name + "</small>" +
            "</div>" +
            "<div class=\"widget-statistic-description\">" +
            "<a href=\"javascript:;\" class=\"am-btn am-btn-secondary\" onclick= viewtimer('" + list[i].seq + "')>" +
            "<i class=\"am-icon-eye\"></i> 查看" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;" +

            "<a href=\"javascript:;\" class=\"am-btn am-btn-warning\" onclick= edittimer('" + list[i].seq + "','" + list[i].status + "')>" +
            "<i class=\"am-icon-pencil\"></i> 编辑" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;" +

            "<a href=\"javascript:;\" class=\"am-btn am-btn-danger\" onclick=deltimer('" + list[i].seq + "','" + list[i].status + "')>" +
            "<i class=\"am-icon-trash\"></i> 删除" +
            "</a>" +
            "</div>" +
            "<a href=\"javascript:;\">" +
            "<span class=\"widget-statistic-icon am-icon-line-chart\" style=\"z-index:99\" onclick=tabletimer('" + list[i].seq + "','" + list[i].name + "','" + list[i].type + "')></span>" +
            "</a></div></div></div>";
        $("#html").append(html);

    }

}

//点击删除调用
function deltimer(seq, status) {

    if (status == "0") {
        alert("无法删除正在运行定时器！");
        return;
    }

    $('#seq').val(seq);
    $.AMUI.progress.start();
    $('#my-confirm').modal({
        relatedTarget: this,
        onConfirm: function () {

            $.ajax({
                url: '/MessageStorage/timer/del.action?seq=' + seq,
                type: 'GET',
                success: function (data) {
                    alert(data.msg);
                    window.location.reload();
                }
            });


        },
        onCancel: function () {

        }
    });
    $.AMUI.progress.done();
}

function getType(type) {
    if (type == "0") {
        return "地面观测";
    } else if (type == "1") {
        return "雷达产品";
    } else if (type == "2") {
        return "卫星产品";
    } else {
        return "未知类型";
    }
}

function selectFunc() {
    var page = $('select  option:selected').val();
    getlist(page);
}

//点击绘图调用
function tabletimer(seq, name, status) {
    $('#tname').text(name);
    $('#my-popup').modal({
        relatedTarget: this
    });
    if (status == "0") {
        var title = "地面观测 - " + name;
    }
    if (status == "1") {
        var title = "雷达产品 - " + name;
    }
    if (status == "2") {
        var title = "卫星产品 - " + name;
    }
    f(seq, title);

}

function f(seq, title) {
    $.getJSON('/MessageStorage/table/timerdata.action?seq=' + seq,
        function (data) {
            var xDate = data.xDate;
            var suc = data.table.suc;
            var fail = data.table.fail;

            // 页面数据
            var pageData = {

                'chart': function chartData() {

                    echarts.init(document.getElementById('tpl-echarts-A'), 'shine').setOption({
                        title: {
                            text: title
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['成功数', '失败数']
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            data: xDate
                        },
                        yAxis: {
                            type: 'value',
                            name: '数量/（个）',
                            interval: 25
                        },
                        series: [{
                            name: '成功数',
                            type: 'line',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            data: suc
                        },
                            {
                                name: '失败数',
                                type: 'line',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'top'
                                    }
                                },
                                data: fail
                            }
                        ]
                    });
                }
            };
            pageData['chart']();


        });
}
