package com.tt.msg.controller;

import com.tt.msg.entity.Manager;
import com.tt.msg.service.ManagerService;
import com.tt.msg.service.TimerService;
import com.tt.msg.utils.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ManagerController
 * @Description Manager模块Controller层
 * @Author tanjiang
 * @CreateTime 2019/1/12 10:50
 * @Version 1.0
 **/

@Controller
@RequestMapping("/user")
public class ManagerController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String USER_SESSION = "manager";

    @Autowired
    private ManagerService managerService;


    @RequestMapping(value = "/login")
    private String login(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_SESSION) != null) {
            return "main";
        }
        String username = HttpServletRequestUtil.getString(request, "username");
        String password = HttpServletRequestUtil.getString(request, "password");
        if (username == null || password == null) {
            request.setAttribute("tip", "请输入用户名和密码！");
            return "login";
        }
        Manager manager = managerService.login(username, password);
        if (manager == null) {
            request.setAttribute("tip", "用户名或密码错误！");
            return "login";
        } else {
            request.getSession().setAttribute(USER_SESSION, manager);
            return "main";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    private String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "/login";
    }

}
