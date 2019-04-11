package com.tt.msg.service;

import com.tt.msg.entity.Record;
import com.tt.msg.entity.RecordForm;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName Record
 * @Description 纪录类接口
 * @Author tanjiang
 * @CreateTime 2019/4/2 8:47
 * @Version 1.0
 **/

public interface RecordService {
    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(Record record);

    /**
     * 获取数据 分页
     *
     * @param recordForm
     * @return
     */
    List<Record> getPage(RecordForm recordForm);

    /**
     * 获取数据 不分页
     *
     * @param recordForm
     * @return
     */
    List<Map<String, String>> getExcelDate(RecordForm recordForm);

    /**
     * 获取数据总数
     *
     * @return
     */
    int getTotal(RecordForm recordForm);

    /**
     * 获取作图信息
     *
     * @return
     */
    Map<String, Object> getTableInfo();

    /**
     * 根据seq查询记录
     *
     * @param seq
     * @return
     */
    Map<String, Object> queryBySeq(Long seq);

    /**
     * 根据timer的seq获取近7天它的数据
     *
     * @return
     */
    Map<String, Object> getTimerInfo(Long timerSeq);
}
