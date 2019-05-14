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
    $.AMUI.progress.start();
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
    $.AMUI.progress.done();
}

//导出excel
function exp() {
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();

    if (startDate != "" && endDate != "" && startDate >= endDate) {
        alert('后面的日期应该大于前面的日期！');
        return;
    }

    var form = window.document.getElementById("excelForm");
    form.action = "/MessageStorage/record/export.action";
    form.submit();
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
                var type = record.type;
                var obj = data.obj;
                if (type == "0") {
                    //地面观测报文成功结果
                    var i = 0;
                    $('#p11').text(obj[i++]);
                    $('#p12').text(obj[i++]);
                    $('#p13').text(obj[i++]);
                    $('#p14').text(obj[i++]);
                    $('#p15').text(obj[i++]);
                    $('#p16').text(obj[i++]);
                    $('#p21').text(obj[i++]);
                    $('#p22').text(obj[i++]);
                    $('#p23').text(obj[i++]);
                    $('#p24').text(obj[i++]);
                    $('#p25').text(obj[i++]);
                    $('#p26').text(obj[i++]);
                    $('#p31').text(obj[i++]);
                    $('#p32').text(obj[i++]);
                    $('#p33').text(obj[i++]);
                    $('#p34').text(obj[i++]);
                    $('#p35').text(obj[i++]);
                    $('#p36').text(obj[i++]);
                    $('#p41').text(obj[i++]);
                    $('#p42').text(obj[i++]);
                    $('#p43').text(obj[i++]);
                    $('#p44').text(obj[i++]);
                    $('#p45').text(obj[i++]);
                    $('#p51').text(obj[i++]);
                    $('#p52').text(obj[i++]);
                    $('#p53').text(obj[i++]);
                    $('#p54').text(obj[i++]);
                    $('#p55').text(obj[i++]);
                    $('#p56').text(obj[i++]);
                    $('#p57').text(obj[i++]);
                    $('#p58').text(obj[i++]);
                    $('#p59').text(obj[i++]);
                    $('#p61').text(obj[i++]);
                    $('#p62').text(obj[i++]);
                    $('#p63').text(obj[i++]);
                    $('#p64').text(obj[i++]);
                    $('#p65').text(obj[i++]);
                    $('#filePath1').text(obj[i]);
                    $('#suc-typeone').modal({
                        relatedTarget: this
                    })
                } else if (type == "1") {
                    //雷达产品报文成功结果
                    $('#picPath').text(obj[0]);
                    $('#filePath2').text(obj[1]);
                    $('#suc-typetwo').modal({
                        relatedTarget: this
                    })
                } else {
                    //卫星产品报文成功结果
                    $('#filedate').text(obj.fileTime);
                    $('#elevation').text(obj.elevation);
                    $('#productid').text(obj.productId);
                    $('#stationid').text(obj.stationId);
                    $('#filePath3').text(obj.filePath);
                    $('#suc-typethree').modal({
                        relatedTarget: this
                    })
                }
            }
        });
}
