package com.tt.msg.controller;

import com.tt.msg.service.RecordService;
import com.tt.msg.service.TimerService;
import com.tt.msg.utils.DateString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TableDateController
 * @Description 用于为图表添加数据
 * @Author tanjiang
 * @CreateTime 2019/4/9 10:22
 * @Version 1.0
 **/

@Controller
@RequestMapping("/table")
public class TableDateController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private TimerService timerService;

    @RequestMapping("/getdata")
    @ResponseBody
    private Map<String, Object> getData(){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = recordService.getTableInfo();
        List<Integer> list = timerService.queryAllStatus();
        //获取作图x轴数据
        ArrayList<String> list1 = DateString.getX(7);
        map.put("xDate",list1);
        map.put("type1Work",list.get(0));
        map.put("type1All",list.get(1));
        map.put("type2Work",list.get(2));
        map.put("type2All",list.get(3));
        map.put("type3Work",list.get(4));
        map.put("type3All",list.get(5));
        map.put("table",map1);
        return map;
    }

    @RequestMapping(value = "/timerdata", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getTimeData(Long seq){
        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, Object> map1 = recordService.getTimerInfo(seq);
        //获取作图x轴数据
        ArrayList<String> list1 = DateString.getXDate(7);
        map.put("xDate",list1);
        map.put("table",map1);
        return map;
    }
}
