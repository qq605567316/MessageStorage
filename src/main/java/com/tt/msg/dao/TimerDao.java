package com.tt.msg.dao;

import com.tt.msg.entity.Timer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @InterfaceName TimerDao
 * @Description 定时器模块dao层接口
 * @Author tanjiang
 * @CreateTime 2019/1/17 9:42
 * @Version 1.0
 **/

public interface TimerDao {
    /**
     * 插入
     *
     * @param timer
     * @return
     */
    int insert(Timer timer);

    /**
     * 删除记录
     *
     * @param seq
     */
    void delete(Long seq);

    /**
     * 更新记录
     *
     * @param timer
     * @return 如果影响行数等于>1，表示更新的记录行数
     */
    int update(Timer timer);

    /**
     * 通过ID查询定时器
     *
     * @param seq
     * @return
     */
    Timer queryBySeq(Long seq);

    /**
     * 通过name查询定时器
     *
     * @param name
     * @return
     */
    Timer queryByName(String name);

    /**
     * 符合条件的分页查询
     *
     * @param name
     * @param type
     * @param startRow
     * @param endRow
     * @return
     */
    List<Timer> queryPage(@Param("name") String name, @Param("type") String type, @Param("startRow") Integer startRow, @Param("endRow") Integer endRow);

    /**
     * @return 返回符合条件的配置的定时器总数
     */
    Integer queryTotal(@Param("name") String name, @Param("type") String type);

    /**
     * 返回各定时器类型的失败与所有定时器数量
     *
     * @return
     */
    List<Integer> queryAllStatus();

    /**
     * 查询所有
     *
     * @return 返回所有定时器
     */
    List<Timer> queryAll();

    /**
     * 用于初始化所有定时器状态为停止
     */
    void initTimers();

    /**
     * 根据timer查询有几个相同功能的timer
     *
     * @param timer
     * @return
     */
    Timer queryByTimer(Timer timer);
}
