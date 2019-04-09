package com.tt.msg.service.impl;

import com.tt.msg.dao.RecordDao;
import com.tt.msg.entity.Record;
import com.tt.msg.entity.RecordForm;
import com.tt.msg.service.RecordService;
import com.tt.msg.utils.DateString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getTableInfo() {
        //构造查询参数，一个包含42个map的数组
        ArrayList<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
        for (int k = 0; k < 3; k++) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("type",""+k);
            for (int j = 0; j < 2; j++) {
                map.put("result",""+j);
                for (int i = 6; i >= 0; i--) {
                    Map<String,Object> map1 = new HashMap<String,Object>();
                    map.put("startDate",DateString.getPastDate(i));
                    map.put("endDate",DateString.getPastDate(i-1));
                    map1.putAll(map);
                    lists.add(map1);
                }
            }
        }
        //查询，返回查询结果
        List<Integer> list = recordDao.getTableInfo(lists);
        //处理查询结果
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("oneFail",list.subList(0,7));
        map2.put("oneSuc",list.subList(7,14));
        map2.put("twoFail",list.subList(14,21));
        map2.put("twoSuc",list.subList(21,28));
        map2.put("threeFail",list.subList(28,35));
        map2.put("threeSuc",list.subList(35,42));
        return map2;
    }
}
