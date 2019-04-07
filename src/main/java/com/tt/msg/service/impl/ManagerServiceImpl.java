package com.tt.msg.service.impl;

import com.tt.msg.dao.ManagerDao;
import com.tt.msg.entity.Manager;
import com.tt.msg.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ManagerServiceImpl
 * @Description Manager模块Service层实现
 * @Author tanjiang
 * @CreateTime 2019/1/12 10:45
 * @Version 1.0
 **/

@Service
public class ManagerServiceImpl implements ManagerService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ManagerDao managerDao;


    @Override public Manager login(String username, String password) {
        return managerDao.login(username, password);
    }
}
