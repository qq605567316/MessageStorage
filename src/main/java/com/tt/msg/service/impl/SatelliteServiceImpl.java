package com.tt.msg.service.impl;

import com.tt.msg.dao.SatelliteDao;
import com.tt.msg.entity.Satellite;
import com.tt.msg.service.SatelliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SatelliteServiceImpl
 * @Description 卫星数据类Service层实现
 * @Author tanjiang
 * @CreateTime 2019/4/12 20:12
 * @Version 1.0
 **/

@Service
public class SatelliteServiceImpl implements SatelliteService {

    @Autowired
    private SatelliteDao satelliteDao;


    @Override
    public int insert(Satellite satellite) {
        return satelliteDao.insert(satellite);
    }
}
