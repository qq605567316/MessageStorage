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
    <title>系统主页</title>
    <meta name="description" content="图表展示页面">
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

<body data-type="chart">
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
                <a href="${path}/user/login.action" class="active">
                    <i class="am-icon-bar-chart sidebar-nav-link-logo"></i> 定时器情况
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="${path}/timer/list.action">
                    <i class="am-icon-table sidebar-nav-link-logo"></i> 配置定时器
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="${path}/record/list.action">
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
                    <div class="page-header-heading">
                        <span class="am-icon-bar-chart sidebar-nav-link-logo"></span> 数据统计
                    </div>
                    <p class="page-header-description">此页面展示定时器情况及最近7天各类定时器处理的记录情况</p>
                </div>
            </div>

        </div>

        <div class="row-content am-cf">

            <div class="row  am-cf">
                <div class="am-u-sm-12 am-u-md-6 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">定时器</div>
                            <div class="widget-function am-fr">
                                <span class="am-fr am-progress-title-more">已启动 / 全部</span>
                            </div>
                        </div>
                        <div class="widget-body widget-body-md am-fr">

                            <div class="am-progress-title">地面观测类型定时器 <span
                                    class="am-fr am-progress-title-more"><label id="s1">14</label> / <label
                                    id="a1">20</label></span>
                            </div>
                            <div class="am-progress">
                                <div class="am-progress-bar" id="d1"></div>
                            </div>
                            <div class="am-progress-title">雷达产品类型定时器 <span
                                    class="am-fr am-progress-title-more"><label id="s2"></label> / <label
                                    id="a2"></label></span>
                            </div>
                            <div class="am-progress">
                                <div class="am-progress-bar  am-progress-bar-warning" id="d2"></div>
                            </div>
                            <div class="am-progress-title">卫星产品类型定时器 <span
                                    class="am-fr am-progress-title-more"><label id="s3"></label> / <label
                                    id="a3"></label></span>
                            </div>
                            <div class="am-progress">
                                <div class="am-progress-bar am-progress-bar-danger" id="d3"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="widget am-cf">
                <div class="widget-head am-cf">
                    <div class="widget-title am-fl">最近7天地面观测类型定时器处理数据情况</div>
                </div>
                <div class="widget-body am-fr">
                    <div style="height: 400px" id="tpl-echarts-A">

                    </div>
                </div>
            </div>

            <div class="widget am-cf">
                <div class="widget-head am-cf">
                    <div class="widget-title am-fl">最近7天雷达产品类型定时器处理数据情况</div>
                </div>
                <div class="widget-body am-fr">
                    <div style="height: 400px" id="tpl-echarts-B">

                    </div>
                </div>
            </div>

            <div class="widget am-cf">
                <div class="widget-head am-cf">
                    <div class="widget-title am-fl">最近7天卫星产品类型定时器处理数据情况</div>
                </div>
                <div class="widget-body am-fr">
                    <div style="height: 400px" id="tpl-echarts-C">

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</div>
<script src="${path}/resources/js/amazeui.min.js"></script>
<script src="${path}/resources/js/amazeui.datatables.min.js"></script>
<script src="${path}/resources/js/dataTables.responsive.min.js"></script>
<script src="${path}/resources/js/main/tabledata.js"></script>
<script src="${path}/resources/js/tabletheme/black.js"></script>
<script src="${path}/resources/js/tabletheme/white.js"></script>

</body>

</html>
