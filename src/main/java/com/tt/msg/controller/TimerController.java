package com.tt.msg.controller;

import com.tt.msg.entity.Timer;
import com.tt.msg.service.TimerService;
import com.tt.msg.utils.HttpServletRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TimerController
 * @Description 定时器模块action层
 * @Author tanjiang
 * @CreateTime 2019/1/15 14:49
 * @Version 1.0
 **/
@Controller
@RequestMapping("/timer")
public class TimerController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否通过数据检查的标识
     */
    private static final String FLAG = "flag";

    @Autowired
    private TimerService timerService;

    @RequestMapping("/list")
    private String listTimers() {
        // WEB-INF/jsp/"timer/list".jsp
        return "timer/list";
    }

    @RequestMapping(value = "/getpage",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> getTimersByPage(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String name = HttpServletRequestUtil.getString(request,"name");
        String type = HttpServletRequestUtil.getString(request,"type");
        if("-1".equals(type)){
            type = null;
        }
        int page = HttpServletRequestUtil.getInt(request, "page");
        List<Timer> timers = timerService.queryPage(name,type,page);
        int total = timerService.queryTotal(name,type);
        modelMap.put("total",total);
        modelMap.put("timers", timers);
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addTimer(@RequestBody Timer timer) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(StringUtils.isBlank(timer.getName())){
            modelMap.put("msg", "添加时出现错误！");
            return modelMap;
        }
        modelMap = this.checkData(timer);
        //判断是否通过检查
        if((Boolean) modelMap.get(FLAG)){
            //新添加的定时器状态默认为停止
            timer.setStatus("1");
            timerService.insert(timer);
            modelMap.put("msg", "添加成功！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> editTimer(@RequestBody Timer timer) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(StringUtils.isBlank(timer.getName())){
            modelMap.put("msg", "修改时出现错误！");
            return modelMap;
        }
        modelMap = this.checkData(timer);
        //判断是否通过检查
        if((Boolean) modelMap.get(FLAG)){
            //新添加的定时器状态默认为停止
            timer.setStatus("1");
            timerService.update(timer);
            modelMap.put("msg", "修改成功！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getBySeq", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getBySeq(Long seq){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (seq == null){
            modelMap.put("success", false);
            modelMap.put("msg", "错误，未接收到传递的Seq！");
            return modelMap;
        }
        Timer timer = timerService.queryBySeq(seq);
        if(timer == null){
            modelMap.put("success", false);
            modelMap.put("msg", "错误，未查询到要编辑的定时器信息！");
        }else {
            modelMap.put("success", true);
            modelMap.put("timer", timer);
        }
        return modelMap;
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> delTimer(Long seq){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (seq == null){
            modelMap.put("msg", "删除时出现错误！");
        }else {
            timerService.delete(seq);
            modelMap.put("msg", "删除成功！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> changeStatus(Long seq,String status){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (seq == null || StringUtils.isBlank(status)){
            modelMap.put("msg", "传递seq或状态时出错！");
        }else {
            timerService.changeStatus(seq,status);
            modelMap.put("msg", "状态改变成功！");
        }
        return modelMap;
    }

    private Map<String, Object> checkData(Timer timer){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        boolean flag = true;
        StringBuilder msg = new StringBuilder();
        //判断名字是否已经存在
        String name = timer.getName();
        Timer t = timerService.queryByName(name);
        if(t != null && !t.getSeq().equals(timer.getSeq())){
            msg.append("名字已经存在");
            flag = false;
        }
        //判断cron表达式是否符合规则
        String cronExpression = timer.getCronExpression();
        if(!CronExpression.isValidExpression(cronExpression)){
            msg.append(" 时间不符合cron表达式规则");
            flag = false;
        }
        //判断文件路径是否存在
        String filePath = timer.getFilePath();
        File file = new File(filePath);
        if(!file.exists()){
            msg.append(" 文件路径不存在");
            flag = false;
        }
        if(!flag){
            msg.append(",请按要求重新操作！");
        }
        modelMap.put("msg",msg.toString());
        modelMap.put(FLAG,flag);
        return modelMap;
    }

}
