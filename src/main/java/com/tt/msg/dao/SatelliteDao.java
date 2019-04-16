package com.tt.msg.dao;

import com.tt.msg.entity.Radar;
import com.tt.msg.entity.Satellite;

/**
 * @InterfaceName SatelliteDao
 * @Description 卫星类型报文Dao层
 * @Author tanjiang
 * @CreateTime 2019/4/12 19:56
 * @Version 1.0
 **/

public interface SatelliteDao {
    /**
     * 插入
     *
     * @param satellite
     * @return
     */
    int insert(Satellite satellite);

    /**
     * 根据seq查询信息
     *
     * @param seq
     * @return
     */
    Radar queryBySeq(Long seq);
}
