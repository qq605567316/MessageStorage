package com.tt.msg.service;


import com.tt.msg.entity.Satellite;

/**
 * @InterfaceName SatelliteService
 * @Description 卫星数据类Service层接口
 * @Author tanjiang
 * @CreateTime 2019/4/12 20:10
 * @Version 1.0
 **/

public interface SatelliteService {
    /**
     * 插入
     *
     * @param satellite
     * @return
     */
    int insert(Satellite satellite);
}
