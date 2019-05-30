<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.tt.msg.entity.Manager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath }" var="path"/>
<%
    Manager manager = (Manager) request.getSession().getAttribute("manager");
    if (manager == null) {
        // 重定向到新地址
        String site = new String("/MessageStorage/user/logout.action");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>记录查询页面</title>
    <meta name="description" content="记录页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="${path}/resources/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="${path}/resources/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <script src="${path}/resources/js/echarts.min.js"></script>
    <link rel="stylesheet" href="${path}/resources/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${path}/resources/css/amazeui.datatables.min.css"/>
    <link rel="stylesheet" href="${path}/resources/css/app.css">
    <script src="${path}/resources/js/jquery.min.js"></script>

</head>

<body data-type="index">
<script src="${path}/resources/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 头部 -->
    <header>
        <!-- logo -->
        <div class="am-fl tpl-header-logo">
            <a href="javascript:;"><img src="${path}/resources/img/logo.png" alt=""></a>
        </div>
        <!-- 右侧内容 -->
        <div class="tpl-header-fluid">
            <!-- 侧边切换 -->
            <div class="am-fl tpl-header-switch-button am-icon-list">
            </div>
            <!-- 其它功能-->
            <div class="am-fr tpl-header-navbar">
                <ul>
                    <!-- 退出 -->
                    <li class="am-text-sm">
                        <a href="${path}/user/logout.action">
                            <span class="am-icon-sign-out"></span> 退出
                        </a>
                    </li>
                </ul>
            </div>
        </div>

    </header>
    <!-- 风格切换 -->
    <div class="tpl-skiner">
        <div class="tpl-skiner-toggle am-icon-cog">
        </div>
        <div class="tpl-skiner-content">
            <div class="tpl-skiner-content-title">
                选择主题
            </div>
            <div class="tpl-skiner-content-bar">
                <span class="skiner-color skiner-white" data-color="theme-white"></span>
                <span class="skiner-color skiner-black" data-color="theme-black"></span>
            </div>
        </div>
    </div>
    <!-- 侧边导航栏 -->
    <div class="left-sidebar">
        <!-- 用户信息 -->
        <div class="tpl-sidebar-user-panel">
            <div class="tpl-user-panel-slide-toggleable">
                <div class="tpl-user-panel-profile-picture">
                    <img src="${path}/resources/img/user02.png" alt="">
                </div>
                <span class="user-panel-logged-in-text">
              <i class="am-icon-circle-o am-text-success tpl-user-panel-status-icon"></i>
              <%= manager.getLevelName()%>
          </span>
            </div>
        </div>

        <!-- 菜单 -->
        <ul class="sidebar-nav">
            <li class="sidebar-nav-link">
                <a href="${path}/user/login.action">
                    <i class="am-icon-bar-chart sidebar-nav-link-logo"></i> 定时器情况
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="${path}/timer/list.action">
                    <i class="am-icon-table sidebar-nav-link-logo"></i> 配置定时器
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="${path}/record/list.action" class="active">
                    <i class="am-icon-calendar sidebar-nav-link-logo"></i> 处理日志
                </a>
            </li>

        </ul>
    </div>


    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">

        <div class="container-fluid am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                    <div class="page-header-heading"><span class="am-icon-table sidebar-nav-link-logo"></span> 展示页面
                        <small>处理记录</small>
                    </div>
                    <p class="page-header-description">在此页面查看处理记录</p>
                </div>
                <div class="am-u-lg-3 tpl-index-settings-button">
                    <button type="button" class="page-header-button" onclick="exp()"><span
                            class="am-icon-file-excel-o"></span> 导出
                    </button>
                </div>
            </div>

        </div>

        <div id="tpl-echarts"></div>

        <div class="row-content am-cf">


            <div class="row">

                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <form class="am-form-inline" role="form" method="post" id="excelForm">

                                <label class="am-form-label">文件名：</label>
                                <div class="am-input-group am-input-group-primary">
                                    <input type="text" class="am-form-field" maxlength="18" id="fileName"
                                           name="fileName">
                                </div>

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <label class="am-form-label">类型：</label>
                                <select data-am-selected="{btnWidth: '8%', btnSize: 'sm', btnStyle: 'secondary'}"
                                        id="type" name="type">
                                    <option value="-1" selected>所有类型</option>
                                    <option value="0">地面观测</option>
                                    <option value="1">雷达产品</option>
                                    <option value="2">卫星产品</option>
                                </select>

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <label class="am-form-label">日期：</label>
                                <div class="am-form-group">
                                    <input size="12" type="text" readonly class="am-form-field" data-am-datepicker
                                           id="startDate" name="startDate">
                                </div>

                                &nbsp;-&nbsp;

                                <div class="am-form-group">
                                    <input size="12" type="text" readonly class="am-form-field" data-am-datepicker
                                           id="endDate" name="endDate">
                                </div>

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <label class="am-form-label">结果：</label>
                                <select data-am-selected="{btnWidth: '8%', btnSize: 'sm', btnStyle: 'secondary'}"
                                        id="result" name="result">
                                    <option value="-1" selected>所有结果</option>
                                    <option value="0">失败</option>
                                    <option value="1">成功</option>
                                </select>

                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <button type="button" class="am-btn am-btn-success" onclick="getlist(1)"><i
                                        class="am-icon-search"></i>查找
                                </button>
                            </form>
                        </div>
                        <div class="widget-body  widget-body-lg am-fr">

                            <table width="100%" class="am-table am-table-compact am-table-striped tpl-table-black ">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>报文类型</th>
                                    <th>处理时间</th>
                                    <th>处理结果</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody id="html">
                                <!-- more data -->


                                </tbody>
                            </table>
                        </div>

                        <%-- 上一页下一页 --%>
                        <div class="am-u-lg-12 am-cf">

                            <div class="am-fr">
                                <ul class="am-pagination tpl-pagination" id="pagination">
                                    <%-- 在这里动态插入上一页下一页 --%>
                                </ul>
                            </div>
                        </div>


                    </div>
                </div>
            </div>


        </div>
    </div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="fail-modal">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">处理详情
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">

            <section data-am-widget="accordion" class="am-accordion am-accordion-default"
                     data-am-accordion='{ "multiple": true }'>
                <dl class="am-accordion-item am-active">
                    <dt class="am-accordion-title">
                        失败原因
                    </dt>
                    <dd class="am-accordion-bd am-collapse am-in">
                        <div class="am-accordion-content am-scrollable-horizontal">
                        <span id="failMsg" class="am-text-danger">

                            </span>
                        </div>
                    </dd>
                </dl>
                <dl class="am-accordion-item">
                    <dt class="am-accordion-title">
                        失败文件现在所在目录及名称
                    </dt>
                    <dd class="am-accordion-bd am-collapse ">
                        <div class="am-accordion-content am-scrollable-horizontal">
                        <span id="failPath">

                            </span>
                        </div>
                    </dd>
                </dl>
            </section>

        </div>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="suc-typeone">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">地面观测报文:</div>
        <section data-am-widget="accordion" class="am-accordion am-accordion-default"
                 data-am-accordion='{ "multiple": true }'>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    观测站基本信息
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        区站号:
                        <span id="p11">

                        </span>
                        观测时间:
                        <span id="p12">

                        </span>
                        纬度:
                        <span id="p13">

                        </span>
                        经度:
                        <span id="p14">

                        </span>
                        观测方式:
                        <span id="p15">

                        </span>
                        文件是否更正过:
                        <span id="p16">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    气压数据
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        本站气压:
                        <span id="p21">

                        </span>
                        海平面气压:
                        <span id="p22">

                        </span>
                        最高本站气压:
                        <span id="p23">

                        </span>
                        最高本站气压出现时间:
                        <span id="p24">

                        </span>
                        最低本站气压:
                        <span id="p25">

                        </span>
                        最低本站气压出现时间:
                        <span id="p26">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    温度数据
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        气温数据:
                        <span id="p31">

                        </span>
                        最高气温:
                        <span id="p32">

                        </span>
                        最高气温出现时间:
                        <span id="p33">

                        </span>
                        最低气温:
                        <span id="p34">

                        </span>
                        最低气温出现时间:
                        <span id="p35">

                        </span>
                        露点温度:
                        <span id="p36">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    湿度和降水数据
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        相对湿度:
                        <span id="p41">

                        </span>
                        最小相对湿度:
                        <span id="p42">

                        </span>
                        最小相对湿度出现时间:
                        <span id="p43">

                        </span>
                        水汽压:
                        <span id="p44">

                        </span>
                        小时降水量:
                        <span id="p45">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    风观测数据
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        两分钟风向:
                        <span id="p51">

                        </span>
                        两分钟平均风速:
                        <span id="p52">

                        </span>
                        十分钟风向:
                        <span id="p53">

                        </span>
                        十分钟平均风速:
                        <span id="p54">

                        </span>
                        最大风速风向:
                        <span id="p55">

                        </span>
                        最大风速:
                        <span id="p56">

                        </span>
                        最大风速出现时间:
                        <span id="p57">

                        </span>
                        瞬时风向:
                        <span id="p58">

                        </span>
                        瞬时风速:
                        <span id="p59">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    地温数据
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        地表温度:
                        <span id="p61">

                        </span>
                        地表最高温度:
                        <span id="p62">

                        </span>
                        地表最高温度出现时间:
                        <span id="p63">

                        </span>
                        地表最低温度:
                        <span id="p64">

                        </span>
                        地表最低温度出现时间:
                        <span id="p65">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item">
                <dt class="am-accordion-title">
                    源文件现在所在目录及名称
                </dt>
                <dd class="am-accordion-bd am-collapse ">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        <span id="filePath1">

                        </span>
                    </div>
                </dd>
            </dl>
        </section>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="suc-typetwo">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">雷达报文:</div>
        <section data-am-widget="accordion" class="am-accordion am-accordion-default"
                 data-am-accordion='{ "multiple": true }'>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    处理生成文件目录位置
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        <span id="picPath">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item">
                <dt class="am-accordion-title">
                    源文件现在所在目录及名称
                </dt>
                <dd class="am-accordion-bd am-collapse ">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        <span id="filePath2">

                        </span>
                    </div>
                </dd>
            </dl>
        </section>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="suc-typethree">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">卫星报文:</div>
        <section data-am-widget="accordion" class="am-accordion am-accordion-default"
                 data-am-accordion='{ "multiple": true }'>
            <dl class="am-accordion-item am-active">
                <dt class="am-accordion-title">
                    报文信息
                </dt>
                <dd class="am-accordion-bd am-collapse am-in">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        收集时间:
                        <span id="filedate">

                        </span>
                        仰角号:
                        <span id="elevation">

                        </span>
                        产品号:
                        <span id="productid">

                        </span>
                        站号:
                        <span id="stationid">

                        </span>
                    </div>
                </dd>
            </dl>
            <dl class="am-accordion-item">
                <dt class="am-accordion-title">
                    源文件现在所在目录及名称
                </dt>
                <dd class="am-accordion-bd am-collapse ">
                    <div class="am-accordion-content am-scrollable-horizontal">
                        <span id="filePath3">

                        </span>
                    </div>
                </dd>
            </dl>
        </section>
    </div>
</div>

<script src="${path}/resources/js/amazeui.min.js"></script>
<script src="${path}/resources/js/amazeui.datatables.min.js"></script>
<script src="${path}/resources/js/dataTables.responsive.min.js"></script>
<script src="${path}/resources/js/app.js"></script>
<script src="${path}/resources/js/record/recordlist.js"></script>

</body>

</html>
