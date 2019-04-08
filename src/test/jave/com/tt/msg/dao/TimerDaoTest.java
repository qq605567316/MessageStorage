package com.tt.msg.dao;

import com.tt.msg.BaseTest;
import com.tt.msg.entity.Timer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName TimerDaoTest
 * @Description time模块dao层测试类
 * @Author tanjiang
 * @CreateTime 2019/1/17 10:03
 * @Version 1.0
 **/

public class TimerDaoTest extends BaseTest {
    @Autowired
    private TimerDao timerDao;

    @Test
    public void insert(){
        Timer timer = new Timer("myTimer","2","0","F:\\CloudMusic","0 15 10 * * ? 2008");
        timerDao.insert(timer);
    }

    @Test
    public void delete(){
        timerDao.delete(1L);
    }

    @Test
    public void queryAll(){
        List<Timer> timers = timerDao.queryAll();
        System.out.println(timers.size());
    }

    @Test
    public void queryBySeq(){
        Timer timer = timerDao.queryBySeq(2L);
        System.out.println(timer.getCronExpression());
    }

    @Test
    public void queryPage(){
        List<Timer> timers = timerDao.queryPage("","",1,6);
        System.out.println(timers.size());
    }

    @Test
    public void queryAllStatus(){
        List<Integer> list = timerDao.queryAllStatus();
        System.out.println(list);
    }
}
