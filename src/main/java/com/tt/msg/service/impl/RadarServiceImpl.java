package com.tt.msg.service.impl;

import com.tt.msg.dao.RadarDao;
import com.tt.msg.entity.Radar;
import com.tt.msg.service.RadarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RadarServiceImpl
 * @Description 雷达数据类Service层实现
 * @Author tanjiang
 * @CreateTime 2019/4/1 9:53
 * @Version 1.0
 **/
@Service
public class RadarServiceImpl implements RadarService {

    @Autowired
    private RadarDao radarDao;

    @Override
    public int insert(Radar radar) {
        return radarDao.insert(radar);
    }
}
