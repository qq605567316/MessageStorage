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
                        alert(data.msg);
                        window.location.href = '/MessageStorage/timer/list.action';
                    }
                });
            },
            onCancel: function () {
                alert('取消添加定时器!');
            }
        });
    });

});
