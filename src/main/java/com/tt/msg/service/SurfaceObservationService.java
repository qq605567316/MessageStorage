package com.tt.msg.service;

import com.tt.msg.entity.SurfaceObservation;

/**
 * @InterfaceName SurfaceObservationService
 * @Description 地面观测数据纪录类Service层接口
 * @Author tanjiang
 * @CreateTime 2019/3/2 10:30
 * @Version 1.0
 **/

public interface SurfaceObservationService {
    /**
     * 插入
     *
     * @param surfaceObservation
     */
    public String insert(SurfaceObservation surfaceObservation);
}
