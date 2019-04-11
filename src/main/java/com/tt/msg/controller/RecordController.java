package com.tt.msg.controller;

import com.tt.msg.entity.*;
import com.tt.msg.service.RecordService;
import com.tt.msg.utils.DateString;
import com.tt.msg.utils.HttpServletRequestUtil;
import com.tt.msg.utils.excel.ViewExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RecordController
 * @Description 纪录类模块action层
 * @Author tanjiang
 * @CreateTime 2019/1/15 15:07
 * @Version 1.0
 **/
@Controller
@RequestMapping("/record")
public class RecordController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecordService recordService;

    @RequestMapping("/list")
    private String listRecords() {
        return "record/list";
    }

    @RequestMapping(value = "/getpage", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> getRecordsByPage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        RecordForm recordForm = this.setValue(request);
        List<Record> records = recordService.getPage(recordForm);
        List<RecordInfo> rInfos = new ArrayList<RecordInfo>();
        for (Record record : records) {
            RecordInfo recordInfo = new RecordInfo(record);
            rInfos.add(recordInfo);
        }
        int total = recordService.getTotal(recordForm);
        modelMap.put("total", total);
        modelMap.put("records", rInfos);
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/getBySeq", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getBySeq(Long seq) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap = recordService.queryBySeq(seq);
        Record record = (Record) modelMap.get("record");
        if (record == null) {
            modelMap.put("success", false);
            modelMap.put("msg", "错误，未查询到该记录信息！");
        } else {
            modelMap.put("success", true);
        }
        return modelMap;
    }

    /**
     * 改变前台传过来的值,让其适应数据库字段
     *
     * @param request
     * @return
     */
    private RecordForm setValue(HttpServletRequest request) {
        RecordForm recordForm = new RecordForm();
        String fileName = HttpServletRequestUtil.getString(request, "fileName");
        String type = HttpServletRequestUtil.getString(request, "type");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String result = HttpServletRequestUtil.getString(request, "result");
        String page = HttpServletRequestUtil.getString(request, "page");
        if (!"".equals(fileName)) {
            recordForm.setFileName(fileName);
        }
        if (!"-1".equals(type)) {
            recordForm.setType(type);
        }
        if (!"".equals(startDate)) {
            startDate += " 00:00:00";
            Timestamp sts = Timestamp.valueOf(startDate);
            recordForm.setStartDate(sts);
        }
        if (!"".equals(endDate)) {
            endDate += " 00:00:00";
            Timestamp ets = Timestamp.valueOf(endDate);
            recordForm.setEndDate(ets);
        }
        if (!"-1".equals(result)) {
            recordForm.setResult(result);
        }
        if(page != null){
            recordForm.setPage(Integer.parseInt(page));
        }
        return recordForm;
    }

    /**
     * 导出
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ModelAndView export(ModelMap map, HttpServletRequest request){
        String fileName = HttpServletRequestUtil.getString(request, "fileName");
        System.out.println(fileName);
        RecordForm recordForm = this.setValue(request);
        List<Map<String, String>> list = recordService.getExcelDate(recordForm);
        String[] titles = {"序号","报文类型","处理时间","处理结果","失败原因","处理文件现所在位置"};
        ViewExcel excel = new ViewExcel(titles);
        map.put("excelList",list);
        return new ModelAndView(excel,map);
    }

}
