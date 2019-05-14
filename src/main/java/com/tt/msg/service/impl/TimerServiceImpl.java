package com.tt.msg.service.impl;

import com.tt.msg.dao.TimerDao;
import com.tt.msg.entity.Timer;
import com.tt.msg.job.FirstJob;
import com.tt.msg.job.SecondJob;
import com.tt.msg.job.ThirdJob;
import com.tt.msg.service.TimerService;
import com.tt.msg.utils.QuartzManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TimerServiceImpl
 * @Description 定时器模块service层实现
 * @Author tanjiang
 * @CreateTime 2019/1/17 10:40
 * @Version 1.0
 **/

@Service
public class TimerServiceImpl implements TimerService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TimerDao timerDao;

    private final static String STATUS_TO_RUN = "0";

    private final static String TIMER_TYPE_ONE = "0";
    private final static String TIMER_TYPE_TWO = "1";
    private final static String TIMER_TYPE_THREE = "2";

    @Override
    public void insert(Timer timer) {
        timerDao.insert(timer);
    }

    @Override
    public void delete(Long seq) {
        timerDao.delete(seq);
    }

    @Override
    public void update(Timer timer) {
        timerDao.update(timer);
    }

    @Override
    public Timer queryBySeq(Long seq) {
        return timerDao.queryBySeq(seq);
    }

    @Override
    public Timer queryByName(String name) {
        return timerDao.queryByName(name);
    }

    @Override
    public List<Timer> queryPage(String name, String type, Integer pageNum) {
        Integer startRow = 9 * pageNum - 8;
        Integer endRow = 9 * pageNum;
        return timerDao.queryPage(name, type, startRow, endRow);
    }

    @Override
    public Integer queryTotal(String name, String type) {
        return timerDao.queryTotal(name, type);
    }

    @Override
    public List<Timer> queryAll() {
        return timerDao.queryAll();
    }

    @Override
    public void initTimers() {
        timerDao.initTimers();
    }

    @Override
    public List<Integer> queryAllStatus() {
        return timerDao.queryAllStatus();
    }

    @Override
    public Map<String, Object> queryByTimer(Timer timer) {
        Map<String,Object> map = new HashMap<String, Object>();
        String filePath = timer.getFilePath();
        String str = filePath.substring(filePath.length() - 1);
        if (File.separator.equals(str)) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }
        timer.setFilePath(filePath);
        Timer t = timerDao.queryByTimer(timer);
        map.put("timer",t);
        //新增逻辑
        if (timer.getSeq() == null) {
            if (t == null) {
                map.put("result",false);
                return map;
            }
            map.put("result",true);
            return map;
        }
        //编辑逻辑
        if (t == null) {
            map.put("result",false);
            return map;
        }
        if (timer.getSeq().equals(t.getSeq())) {
            map.put("result",false);
            return map;
        }
        map.put("result",true);
        return map;
    }

    @Override
    public void changeStatus(Long seq, String status) {
        Timer timer = timerDao.queryBySeq(seq);
        if (STATUS_TO_RUN.equals(status)) {
            this.startUp(timer);
        } else {
            this.shutDown(timer);
        }
        timer.setStatus(status);
        timerDao.update(timer);
    }

    /**
     * 启动定时任务
     *
     * @param timer
     * @return
     */
    private boolean startUp(Timer timer) {
        Long seq = timer.getSeq();
        String name = timer.getName();
        String type = timer.getType();
        String filePath = timer.getFilePath();
        String cronExpression = timer.getCronExpression();
        if (TIMER_TYPE_ONE.equals(type)) {
            QuartzManager.addJob(seq, name, FirstJob.class, cronExpression, filePath);
            return true;
        } else if (TIMER_TYPE_TWO.equals(type)) {
            QuartzManager.addJob(seq, name, SecondJob.class, cronExpression, filePath);
            return true;
        } else if (TIMER_TYPE_THREE.equals(type)) {
            QuartzManager.addJob(seq, name, ThirdJob.class, cronExpression, filePath);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 关闭定时任务
     *
     * @param timer
     */
    private void shutDown(Timer timer) {
        String name = timer.getName();
        QuartzManager.removeJob(name);
    }

}
