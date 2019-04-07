package com.tt.msg.service.impl;

import com.tt.msg.dao.RecordDao;
import com.tt.msg.entity.Record;
import com.tt.msg.entity.RecordForm;
import com.tt.msg.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RecordServiceImpl
 * @Description 记录类Service层实现
 * @Author tanjiang
 * @CreateTime 2019/4/2 10:12
 * @Version 1.0
 **/
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordDao recordDao;


    @Override
    public int insert(Record record) {
        return recordDao.insert(record);
    }

    @Override
    public List<Record> getPage(RecordForm recordForm) {
        int page = recordForm.getPage();
        Integer startRow = 10 * page - 9;
        Integer endRow = 10 * page;
        return recordDao.getPage(recordForm, startRow, endRow);
    }

    @Override
    public int getTotal(RecordForm recordForm) {
        return recordDao.getTotal(recordForm);
    }
}
