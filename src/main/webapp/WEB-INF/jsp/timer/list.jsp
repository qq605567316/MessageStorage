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
    <title>定时器管理页面</title>
    <meta name="description" content="定时器管理页面">
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
            <div class="am-fl tpl-header-switch-button am-icon-list"></div>
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
                <a href="${path}/timer/list.action" class="active">
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
                    <div class="page-header-heading"><span class="am-icon-table sidebar-nav-link-logo"></span> 管理页面
                        <small>定时器</small>
                    </div>
                    <p class="page-header-description">在此页面对定时器进行管理</p>
                </div>
                <div class="am-u-lg-3 tpl-index-settings-button">
                    <button type="button" class="page-header-button" id="doc-prompt-toggle"><span
                            class="am-icon-plus"></span> 新增
                    </button>
                </div>
            </div>

        </div>

        <div class="row-content am-cf" id="tpl-echarts">
            <div class="row  am-cf">

            </div>
        </div>

        <div class="widget am-cf">

            <div class="widget-head am-cf">
                <form class="am-form-inline" role="form">

                    <label class="am-form-label">定时器名称：</label>
                    <div class="am-input-group am-input-group-primary">
                        <input type="text" class="am-form-field" maxlength="6" id="timername">
                    </div>

                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                    <label class="am-form-label">定时器类型：</label>
                    <select data-am-selected="{btnWidth: '8%', btnSize: 'sm', btnStyle: 'secondary'}" id="timertype">
                        <option value="-1" selected>所有类型</option>
                        <option value="0">地面观测</option>
                        <option value="1">雷达产品</option>
                        <option value="2">卫星产品</option>
                    </select>

                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                    <button type="button" class="am-btn am-btn-success" onclick="getlist(1)"><i
                            class="am-icon-search"></i>查找
                    </button>
                </form>
            </div>

            <div class="row-content am-cf">
                <div class="row  am-cf">
                    <div id="html">

                    </div>
                </div>
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

<form enctype="multipart/form-data">
    <div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt">

        <div class="am-modal-dialog">
            <div class="am-modal-hd">新增定时器:</div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">报文类型</label>
                <select data-am-selected name="type" id="type">
                    <option value="0">地面观测</option>
                    <option value="1">雷达产品</option>
                    <option value="2">卫星产品</option>
                </select>
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">名称</label>
                <input type="text" class="am-modal-prompt-input" placeholder="定时器名称" name="name" id="name"
                       maxlength="6">
            </div>
            <div class="am-modal-bd">
                <a href="http://cron.qqe2.com/" target="_blank" for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析频次</a>
                <input type="text" class="am-modal-prompt-input" placeholder="cron表达式" name="cronExpression"
                       id="cronExpression">
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析路径</label>
                <input type="text" class="am-modal-prompt-input" placeholder="解析路径" name="filePath" id="filePath">
            </div>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm>提交</span>
            </div>

        </div>
    </div>
</form>


<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt2">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">修改定时器:</div>
        <form enctype="multipart/form-data">
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">报文类型</label>
                <select data-am-selected name="type" id="edittype">
                    <%--<select name="type" id="edittype">--%>
                    <option value="0">地面观测</option>
                    <option value="1">雷达产品</option>
                    <option value="2">卫星产品</option>
                </select>
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">名称</label>
                <input type="text" class="am-modal-prompt-input" placeholder="定时器名称" name="name" id="editname"
                       maxlength="6">
            </div>
            <div class="am-modal-bd">
                <a href="http://cron.qqe2.com/" target="_blank" for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析频次</a>
                <input type="text" class="am-modal-prompt-input" placeholder="cron表达式" name="cronExpression"
                       id="editcronExpression">
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析路径</label>
                <input type="text" class="am-modal-prompt-input" placeholder="解析路径" name="filePath" id="editfilePath">
            </div>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm>提交</span>
            </div>
        </form>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="my-prompt3">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">定时器信息:</div>
        <form enctype="multipart/form-data">
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">报文类型</label>
                <select data-am-selected name="type" id="viewtype" disabled="disabled">
                    <%--<select name="type" id="edittype">--%>
                    <option value="0">地面观测</option>
                    <option value="1">雷达产品</option>
                    <option value="2">卫星产品</option>
                </select>
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">名称</label>
                <input type="text" disabled="disabled" class="am-modal-prompt-input" placeholder="定时器名称" name="name"
                       id="viewname" maxlength="6">
            </div>
            <div class="am-modal-bd">
                <a href="http://cron.qqe2.com/" target="_blank" for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析频次</a>
                <input type="text" disabled="disabled" class="am-modal-prompt-input" placeholder="cron表达式"
                       name="cronExpression" id="viewExpression">
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析路径</label>
                <input type="text" disabled="disabled" class="am-modal-prompt-input" placeholder="解析路径" name="filePath"
                       id="viewfilePath">
            </div>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>退出</span>
            </div>
        </form>
    </div>
</div>

<div class="am-popup" id="my-popup">
    <div class="am-popup-inner">
        <div class="am-popup-hd">
            <h4 class="am-popup-title"><span id="tname"></span>定时器最近七天处理情况</h4>
            <span data-am-modal-close
                  class="am-close">x</span>
        </div>
        <div class="am-popup-bd">
            <div style="height: 400px" id="tpl-echarts-A">

            </div>
        </div>
    </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
    <div class="am-modal-dialog">
        <div class="am-modal-bd">
            删除定时器
        </div>
        <div class="am-modal-bd">
            确定要删除这个定时器吗？<br/><br/>
            <input type="hidden" class="am-modal-prompt-input" id="seq" value="">
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="nextF">
    <div class="am-modal-dialog">
        <div class="am-modal-bd">
            定时器功能冲突
        </div>
        <div class="am-modal-bd">
            该目录已存在此类定时器，是否通过修改已存在定时器的cron来替换该操作？<br/><br/>
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="nextF2">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">现存在的同目录同功能定时器:</div>
        <form enctype="multipart/form-data">
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">报文类型</label>
                <select data-am-selected name="type" id="etype" disabled>
                    <%--<select name="type" id="edittype">--%>
                    <option value="0">地面观测</option>
                    <option value="1">雷达产品</option>
                    <option value="2">卫星产品</option>
                </select>
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">名称</label>
                <input type="text" class="am-modal-prompt-input" placeholder="定时器名称" name="name" id="ename"
                       maxlength="6" disabled>
            </div>
            <div class="am-modal-bd">
                <a href="http://cron.qqe2.com/" target="_blank" for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析频次</a>
                <input type="text" class="am-modal-prompt-input" placeholder="cron表达式" name="cronExpression"
                       id="ecronExpression">
            </div>
            <div class="am-modal-bd">
                <label for="doc-ipt-3" class="am-u-sm-2 am-form-label">解析路径</label>
                <input type="text" class="am-modal-prompt-input" placeholder="解析路径" name="filePath" id="efilePath" disabled>
            </div>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm>提交</span>
            </div>
        </form>
    </div>
</div>

<script src="${path}/resources/js/amazeui.min.js"></script>
<script src="${path}/resources/js/amazeui.datatables.min.js"></script>
<script src="${path}/resources/js/dataTables.responsive.min.js"></script>
<script src="${path}/resources/js/app.js"></script>
<script src="${path}/resources/js/timer/timerlist.js"></script>
<script src="${path}/resources/js/timer/timeradd.js"></script>
<script src="${path}/resources/js/tabletheme/shine.js"></script>
</body>

</html>
