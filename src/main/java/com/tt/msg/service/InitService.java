package com.tt.msg.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName InitService
 * @Description 项目初始化执行
 * @Author tanjiang
 * @CreateTime 2019/4/7 11:26
 * @Version 1.0
 **/

@Service
public class InitService implements InitializingBean {

    @Autowired
    private TimerService timerService;

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化定时器状态
        timerService.initTimers();
    }
}
