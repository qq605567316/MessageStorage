package com.tt.msg.service;

import com.tt.msg.entity.Record;
import com.tt.msg.entity.RecordForm;

import java.util.List;

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
     * @param record
     * @return
     */
    int insert(Record record);

    /**
     * 获取数据
     * @param recordForm
     * @return
     */
    List<Record> getPage(RecordForm recordForm);

    /**
     * 获取数据总数
     * @return
     */
    int getTotal(RecordForm recordForm);
}
