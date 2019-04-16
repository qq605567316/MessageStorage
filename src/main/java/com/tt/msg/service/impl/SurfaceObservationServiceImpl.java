package com.tt.msg.service.impl;

import com.tt.msg.dao.SurfaceObservationDao;
import com.tt.msg.entity.SurfaceObservation;
import com.tt.msg.service.SurfaceObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ClassName SurfaceObservationServiceImpl
 * @Description 地面观测数据纪录类Service层实现
 * @Author tanjiang
 * @CreateTime 2019/3/2 10:33
 * @Version 1.0
 **/
@Service
public class SurfaceObservationServiceImpl implements SurfaceObservationService {

    @Autowired
    private SurfaceObservationDao surfaceObservationDao;


    @Override
    public String insert(SurfaceObservation surfaceObservation) {
        try {
            surfaceObservationDao.insert(surfaceObservation);
            return "s";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
