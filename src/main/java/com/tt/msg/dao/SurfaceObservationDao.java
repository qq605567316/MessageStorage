package com.tt.msg.dao;

import com.tt.msg.entity.SurfaceObservation;

/**
 * @InterfaceName SurfaceObservationDao
 * @Description 地面观测数据纪录类Dao层接口
 * @Author tanjiang
 * @CreateTime 2019/3/2 10:21
 * @Version 1.0
 **/

public interface SurfaceObservationDao {
    /**
     * 插入
     * @param surfaceObservation
     * @return
     */
    int insert(SurfaceObservation surfaceObservation);
}
