package com.tt.msg.service;

import com.tt.msg.entity.Radar;

/**
 * @InterfaceName RadarService
 * @Description 雷达反演产品生成图片Service层
 * @Author tanjiang
 * @CreateTime 2019/3/20 13:25
 * @Version 1.0
 **/

public interface RadarService {
    /**
     * 插入
     * @param radar
     * @return
     */
    int insert(Radar radar);
}
