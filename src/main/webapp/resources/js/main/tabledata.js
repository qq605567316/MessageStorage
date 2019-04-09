$(function() {
    autoLeftNav();
    $(window).resize(function() {
        autoLeftNav();
        console.log($(window).width())
    });
});

$.getJSON('/MessageStorage/table/getdata.action',
    function (data) {

        var workone = data.type1Work;
        var allone = data.type1All;
        var worktwo = data.type2Work;
        var alltwo = data.type2All;
        var workthree = data.type3Work;
        var allthree = data.type3All;

        var sty1 = workone/allone;
        var sty2 = worktwo/alltwo;
        var sty3 = workthree/allthree;

        $("#d1").css("width",sty1*100+"%");
        $("#d2").css("width",sty2*100+"%");
        $("#d3").css("width",sty3*100+"%");

        $("#s1").text(workone);
        $("#a1").text(allone);
        $("#s2").text(worktwo);
        $("#a2").text(alltwo);
        $("#s3").text(workthree);
        $("#a3").text(allthree);

        var xDate = data.xDate;
        var oneSuc = data.table.oneSuc;
        var oneFail = data.table.oneFail;
        var twoSuc = data.table.twoSuc;
        var twoFail = data.table.twoFail;
        var threeSuc = data.table.threeSuc;
        var threeFail = data.table.threeFail;

        // 页面数据
        var pageData = {
            'chart': function chartData() {

                //类型一数据
                var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));
                optionA = {
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
                        interval: 10
                    },
                    series: [{
                        name: '成功数',
                        type: 'line',
                        data: oneSuc
                    },
                        {
                            name: '失败数',
                            type: 'line',
                            data: oneFail
                        }
                    ]
                };
                echartsA.setOption(optionA);


                //类型二数据
                var echartsB = echarts.init(document.getElementById('tpl-echarts-B'));
                optionB = {
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
                        interval: 10
                    },
                    series: [{
                        name: '成功数',
                        type: 'bar',
                        data: twoSuc
                    },
                        {
                            name: '失败数',
                            type: 'bar',
                            data: twoFail
                        }
                    ]
                };
                echartsB.setOption(optionB);


                //类型三数据
                var echartsC = echarts.init(document.getElementById('tpl-echarts-C'));
                optionC = {
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['成功数', '失败数']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
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
                        interval: 10
                    },
                    series: [
                        {
                            name:'成功数',
                            type:'line',
                            step: 'start',
                            data:threeSuc
                        },
                        {
                            name:'失败数',
                            type:'line',
                            step: 'end',
                            data:threeFail
                        }
                    ]
                };
                echartsC.setOption(optionC);

            }
        };

        // 读取body data-type 判断是哪个页面然后执行相应页面方法，方法在下面。
        var dataType = $('body').attr('data-type');
        console.log(dataType);
        for (key in pageData) {
            if (key == dataType) {
                pageData[key]();
            }
        }
    });






// 风格切换
$('.tpl-skiner-toggle').on('click', function() {
    $('.tpl-skiner').toggleClass('active');
});
$('.tpl-skiner-content-bar').find('span').on('click', function() {
    $('body').attr('class', $(this).attr('data-color'));
    saveSelectColor.Color = $(this).attr('data-color');
    // 保存选择项
    storageSave(saveSelectColor);

});

// 侧边菜单开关
function autoLeftNav() {

    $('.tpl-header-switch-button').on('click', function() {
        if ($('.left-sidebar').is('.active')) {
            if ($(window).width() > 1024) {
                $('.tpl-content-wrapper').removeClass('active');
            }
            $('.left-sidebar').removeClass('active');
        } else {

            $('.left-sidebar').addClass('active');
            if ($(window).width() > 1024) {
                $('.tpl-content-wrapper').addClass('active');
            }
        }
    });

    if ($(window).width() < 1024) {
        $('.left-sidebar').addClass('active');
    } else {
        $('.left-sidebar').removeClass('active');
    }
}

// 侧边菜单
$('.sidebar-nav-sub-title').on('click', function() {
    $(this).siblings('.sidebar-nav-sub').slideToggle(80)
        .end()
        .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
});
