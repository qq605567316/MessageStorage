package com.tt.msg.service;

import com.tt.msg.entity.Timer;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName TimerService
 * @Description 定时器模块Service层接口
 * @Author tanjiang
 * @CreateTime 2019/1/17 10:34
 * @Version 1.0
 **/

public interface TimerService {
    /**
     * 插入新增
     *
     * @param timer
     */
    public void insert(Timer timer);

    /**
     * 根据序列删除
     *
     * @param seq
     */
    public void delete(Long seq);

    /**
     * 修改操作
     *
     * @param timer
     */
    public void update(Timer timer);

    /**
     * 启动或停止定时器
     *
     * @param seq
     * @param status
     */
    public void changeStatus(Long seq, String status);

    /**
     * 通过ID查询定时器
     *
     * @param seq
     * @return
     */
    public Timer queryBySeq(Long seq);

    /**
     * 通过name查询定时器
     *
     * @param name
     * @return
     */
    public Timer queryByName(String name);

    /**
     * 根据条件分页查询
     *
     * @param name
     * @param type
     * @param pageNum
     * @return
     */
    public List<Timer> queryPage(String name, String type, Integer pageNum);

    /**
     * 查询符合条件的定时器总数
     *
     * @param name
     * @param type
     * @return
     */
    public Integer queryTotal(String name, String type);

    /**
     * 查询所有定时器
     *
     * @return 所有定时器
     */
    public List<Timer> queryAll();

    /**
     * 用于初始化定时器状态
     */
    public void initTimers();

    /**
     * 返回各定时器类型的失败与所有定时器数量
     *
     * @return
     */
    List<Integer> queryAllStatus();

    /**
     * 检查是否有相同的timer
     *
     * @param timer
     * @return
     */
    Map<String, Object> queryByTimer(Timer timer);
}
