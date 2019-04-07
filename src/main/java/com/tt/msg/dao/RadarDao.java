package com.tt.msg.dao;

import com.tt.msg.entity.Radar;

/**
 * @InterfaceName RadarDao
 * @Description 雷达反演产品生成图片Dao层
 * @Author tanjiang
 * @CreateTime 2019/4/1 9:19
 * @Version 1.0
 **/

public interface RadarDao {
    /**
     * 插入
     * @param radar
     * @return
     */
    int insert(Radar radar);
}
