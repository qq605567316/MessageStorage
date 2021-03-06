package com.tt.msg.dao;

import com.tt.msg.entity.Record;
import com.tt.msg.entity.RecordForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName RecordDao
 * @Description 处理记录Dao层
 * @Author tanjiang
 * @CreateTime 2019/3/29 10:39
 * @Version 1.0
 **/

public interface RecordDao {
    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(Record record);

    /**
     * 分页查询
     *
     * @param recordForm
     * @param startRow
     * @param endRow
     * @return
     */
    List<Record> getPage(@Param("record") RecordForm recordForm, @Param("startRow") Integer startRow, @Param("endRow") Integer endRow);

    /**
     * 获取总记录数
     *
     * @param recordForm
     * @return
     */
    int getTotal(RecordForm recordForm);

    /**
     * 获取作图需要的信息
     *
     * @param list
     * @return
     */
    List<Integer> getTableInfo(List<Map<String, Object>> list);

    /**
     * 根据seq查询Record
     *
     * @param seq
     * @return
     */
    Record queryBySeq(Long seq);

    /**
     * 根据timer的序号获取所有它所处理的数据
     *
     * @param list
     * @param timerSeq
     * @return
     */
    List<Integer> getTimerInfo(@Param("list") List<Map<String, Object>> list, @Param("timerSeq") Long timerSeq);

    /**
     * 查询所有符合条件的数据
     *
     * @param recordForm
     * @return
     */
    List<Record> getExcelDate(@Param("record") RecordForm recordForm);
}
