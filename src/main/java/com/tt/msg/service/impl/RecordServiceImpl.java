package com.tt.msg.service.impl;

import com.tt.msg.dao.RadarDao;
import com.tt.msg.dao.RecordDao;
import com.tt.msg.dao.SatelliteDao;
import com.tt.msg.dao.SurfaceObservationDao;
import com.tt.msg.entity.*;
import com.tt.msg.service.RecordService;
import com.tt.msg.utils.DateString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Autowired
    private RadarDao radarDao;

    @Autowired
    private SatelliteDao satelliteDao;

    @Autowired
    private SurfaceObservationDao surfaceObservationDao;

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
    public List<Map<String, String>> getExcelDate(RecordForm recordForm) {
        List<Record> records = recordDao.getExcelDate(recordForm);
        //处理取到的数据
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (Record record : records) {
            Map<String, String> map = new HashMap<String, String>();
            RecordInfo recordInfo = new RecordInfo(record);
            map.put("序号", recordInfo.getSeq().toString());
            map.put("报文类型", recordInfo.getType());
            map.put("处理时间", recordInfo.getDelDate());
            map.put("处理文件现所在位置", recordInfo.getFileName());
            map.put("处理结果", recordInfo.getResult());
            if("0".equals(record.getResult())){
                map.put("失败原因", recordInfo.getFailMsg());
            }else {
                map.put("失败原因", "");
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public int getTotal(RecordForm recordForm) {
        return recordDao.getTotal(recordForm);
    }

    @Override
    public Map<String, Object> getTableInfo() {
        //构造查询参数，一个包含42个map的数组
        ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        for (int k = 0; k < 3; k++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("type", "" + k);
            for (int j = 0; j < 2; j++) {
                map.put("result", "" + j);
                for (int i = 6; i >= 0; i--) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map.put("startDate", DateString.getPastDate(i));
                    map.put("endDate", DateString.getPastDate(i - 1));
                    map1.putAll(map);
                    lists.add(map1);
                }
            }
        }
        //查询，返回查询结果
        List<Integer> list = recordDao.getTableInfo(lists);
        //处理查询结果
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("oneFail", list.subList(0, 7));
        map2.put("oneSuc", list.subList(7, 14));
        map2.put("twoFail", list.subList(14, 21));
        map2.put("twoSuc", list.subList(21, 28));
        map2.put("threeFail", list.subList(28, 35));
        map2.put("threeSuc", list.subList(35, 42));
        return map2;
    }

    @Override
    public Map<String, Object> queryBySeq(Long seq) {
        Map<String, Object> map = new HashMap<String, Object>();
        Record record = recordDao.queryBySeq(seq);
        String result = record.getResult();
        map.put("record", record);
        if ("0".equals(result)) {
            map.put("rs", false);
            return map;
        }
        map.put("rs", true);
        Long sucSeq = record.getSucSeq();
        String type = record.getType();
        if ("0".equals(type)) {
            SurfaceObservation surfaceObservation = surfaceObservationDao.queryBySeq(sucSeq);
            List<String> list = surfaceObservation.dealSurface(surfaceObservation);
            list.add(record.getFileName());
            map.put("obj", list);
        }
        if ("1".equals(type)) {
            Radar radar = radarDao.queryBySeq(sucSeq);
            List<String> list = new ArrayList<String>();
            File f = new File(radar.getGdrFilePath());
            list.add(f.getParent());
            list.add(record.getFileName());
            map.put("obj", list);
        }
        if ("2".equals(type)) {
            Satellite satellite = satelliteDao.queryBySeq(sucSeq);
            satellite.setFileTime(DateString.getFullString(satellite.getFileDate()));
            map.put("obj",satellite);
        }

        return map;
    }

    @Override
    public Map<String, Object> getTimerInfo(Long timerSeq) {
        //构造查询参数，一个包含14个map的数组
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        for (int j = 0; j < 2; j++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", "" + j);
            for (int i = 6; i >= 0; i--) {
                Map<String, Object> dateMap = new HashMap<String, Object>();
                map.put("startDate", DateString.getPastDate(i));
                map.put("endDate", DateString.getPastDate(i - 1));
                dateMap.putAll(map);
                lists.add(dateMap);
            }
        }
        //查询，返回查询结果
        List<Integer> list = recordDao.getTimerInfo(lists, timerSeq);
        //处理查询结果
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("fail", list.subList(0, 7));
        map2.put("suc", list.subList(7, 14));
        return map2;
    }


}
