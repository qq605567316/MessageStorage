package com.tt.msg.dao;

import com.tt.msg.BaseTest;
import com.tt.msg.entity.Record;
import com.tt.msg.entity.RecordForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RecordDao
 * @Description TODO
 * @Author tanjiang
 * @CreateTime 2019/4/5 22:01
 * @Version 1.0
 **/

public class RecordDaoTest extends BaseTest {
    @Autowired
    private RecordDao recordDao;

    @Test
    public void query(){
        RecordForm recordForm = new RecordForm();
//        recordForm.setType("1");
//        recordForm.setResult("1");
        String stStr = "2019-04-01 11:49:45";
        String edStr = "2019-04-03 11:49:45";
        Timestamp st = Timestamp.valueOf(stStr);
        Timestamp ed = Timestamp.valueOf(edStr);
        recordForm.setStartDate(st);
        recordForm.setEndDate(ed);
        List<Record> records = recordDao.getPage(recordForm,1,99);
        System.out.println(records);
    }

    @Test
    public void getTotal(){
        RecordForm recordForm = new RecordForm();
//        recordForm.setType("1");
//        recordForm.setResult("1");
        String stStr = "2019-04-01 11:49:45";
        String edStr = "2019-04-03 11:49:45";
        Timestamp st = Timestamp.valueOf(stStr);
        Timestamp ed = Timestamp.valueOf(edStr);
        recordForm.setStartDate(st);
        recordForm.setEndDate(ed);
        int t = recordDao.getTotal(recordForm);
        System.out.println(t);
    }



}
