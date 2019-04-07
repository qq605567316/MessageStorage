package com.tt.msg.service;

import com.tt.msg.entity.Manager;

/**
 * @InterfaceName ManagerService
 * @Description Manager模块Service层接口
 * @Author tanjiang
 * @CreateTime 2019/1/12 10:42
 * @Version 1.0
 **/


public interface ManagerService {
    /**
     * 用户登录验证
     * @param username
     * @param password
     * @return
     */
    public Manager login(String username, String password);
}
