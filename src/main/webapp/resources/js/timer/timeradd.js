$(function () {
    //从后台获得select组件的数据
    var addTimerUrl = '/MessageStorage/timer/add.action';

    $('#doc-prompt-toggle').on('click', function () {

        $('#my-prompt').modal({
            relatedTarget: this,
            onConfirm: function () {
                var typeVal = $('#type').val();
                var nameVal = $('#name').val();
                var cronExpressionVal = $('#cronExpression').val();
                var filePathVal = $('#filePath').val();
                var timer = {type: typeVal, name: nameVal, cronExpression: cronExpressionVal, filePath: filePathVal};
                $.ajax({
                    url: addTimerUrl,
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
            },
            onCancel: function () {
                alert('取消添加定时器!');
            }
        });
    });

});

//当添加或编辑时定时器功能冲突调用
function nextF(data) {
    $('#nextF').modal({
        relatedTarget: this,
        onConfirm: function () {
            if(data.lv == 1){
                alert('需要超级管理员权限!');
            }else{
                nextF2(data);
            }
        },
        onCancel: function () {
            alert('取消添加定时器!');
        }
    });
}

function nextF2(data) {
    $("#etype").find("option[value='" + data.timer.type + "']").attr("selected", true);
    $("#etype").trigger('changed.selected.amui');
    $('#ename').val(data.timer.name);
    $('#ecronExpression').val(data.timer.cronExpression);
    $('#efilePath').val(data.timer.filePath);
    $('#nextF2').modal({
        relatedTarget: this,
        onConfirm: function () {
            var typeVal = $('#etype').val();
            var nameVal = $('#ename').val();
            var cronExpressionVal = $('#ecronExpression').val();
            var filePathVal = $('#efilePath').val();
            var status = data.timer.status;
            var seq = data.timer.seq;
            var timer = {
                type: typeVal,
                name: nameVal,
                cronExpression: cronExpressionVal,
                filePath: filePathVal,
                status: status,
                seq: seq
            };
            $.AMUI.progress.start();
            $.ajax({
                url: '/MessageStorage/timer/nextedit.action',
                type: 'POST',
                data: JSON.stringify(timer),
                contentType: 'application/json;charset=utf-8',
                success: function (data) {
                    alert(data.msg);
                    window.location.reload();
                }
            });
            $.AMUI.progress.done();
        },
        onCancel: function () {
            alert('取消编辑定时器信息!');
        }
    });
}
