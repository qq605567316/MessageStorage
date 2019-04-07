package com.tt.msg.dao;

import com.tt.msg.BaseTest;
import com.tt.msg.entity.Manager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ManagerTest
 * @Description Manager模块dao层测试类
 * @Author tanjiang
 * @CreateTime 2019/1/12 14:51
 * @Version 1.0
 **/

public class ManagerDaoTest extends BaseTest {

    @Autowired
    private ManagerDao managerDao;

    @Test
    public void login(){
        Manager manager = managerDao.login("admin","123123");
        System.out.println(manager.getLevelName());
    }
}
