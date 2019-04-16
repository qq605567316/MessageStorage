package com.tt.msg.job;

import com.tt.msg.entity.Record;
import com.tt.msg.entity.Satellite;
import com.tt.msg.service.RecordService;
import com.tt.msg.service.SatelliteService;
import com.tt.msg.utils.ApplicationContextHelper;
import com.tt.msg.utils.DateString;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * @ClassName ThirdJob
 * @Description 用于处理第三种类型报文 卫星类型报文
 * @Author tanjiang
 * @CreateTime 2019/2/25 22:07
 * @Version 1.0
 **/

public class ThirdJob implements Job {

    private SatelliteService satelliteService = ApplicationContextHelper.getBean(SatelliteService.class);
    private RecordService recordService = ApplicationContextHelper.getBean(RecordService.class);

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 解析失败的路径
     */
    private final String failPath = File.separator + "type_three_fail" + File.separator;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String filePath = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("filePath");
        File dir = new File(filePath);
        Long timerSeq = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get("timerSeq");
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return realFile(name);
            }
        };
        //获取所有同类文件
        File[] files = dir.listFiles(fileFilter);
        if (files == null) {
            return;
        }
        //每个文件单独处理
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            this.toDealFile(file, timerSeq);
        }
    }

    /**
     * 具体处理方法
     * @param file
     * @param timerSeq
     */
    private void toDealFile(File file, Long timerSeq) {
        String fileName = file.getName();
        String[] parameter = fileName.split("\\.");
        String sTime = parameter[0].substring(0,4)+"-"+parameter[0].substring(4,6)+"-"+parameter[0].substring(6,8)+" "
                +parameter[1].substring(0,2)+":"+parameter[1].substring(2,4)+":"+parameter[1].substring(4,6);
        Timestamp ts = Timestamp.valueOf(sTime);
        //正在处理的源文件路径
        String filePath = file.getParent();
        //成功文件夹
        File successFile = new File(filePath + File.separator + parameter[4] + File.separator + DateString.getFileDefaultString(new Date()) + "_" + file.getName());
        //获取处理文件成功时的路径
        String sucPath = successFile.getAbsolutePath();
        //处理file
        try {
            FileUtils.moveFile(file,successFile);
            Satellite satellite = new Satellite(ts,parameter[2],parameter[3],parameter[4],sucPath);
            satelliteService.insert(satellite);
            Record record = new Record(timerSeq,sucPath,"2",satellite.getSeq());
            recordService.insert(record);
            LOGGER.info(file.getName()+"处理成功！");
        } catch (IOException e) {
            LOGGER.info(file.getName()+"处理失败！");
            //失败文件夹
            File failFile = new File(filePath + failPath + DateString.getFileDefaultString(new Date()) + "_" + file.getName());
            //获取处理文件失败时的路径
            String failPath = failFile.getAbsolutePath();
            try {
                FileUtils.moveFile(file,failFile);
                Record record = new Record(timerSeq,failPath,"2","文件处理失败，源文件移动到成功文件夹失败。");
                recordService.insert(record);
                LOGGER.error(file.getName()+"处理失败，文件从"+file.getAbsolutePath()+"移动到"+sucPath+"失败："+e.getMessage());
            } catch (IOException e1) {
                Record record = new Record(timerSeq,failPath,"2","文件处理失败，源文件移动到失败文件夹失败。");
                LOGGER.error(file.getName()+"处理失败了，文件从"+file.getAbsolutePath()+"移动到"+failPath+"失败："+e1.getMessage());
            }
        }
    }

    /**
     * 正则表达式找出符合条件的文件
     * @param fileName
     * @return
     */
    public boolean realFile(String fileName){
        String fileRegex = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229))\\.([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])\\.\\d{2}\\.\\d{2}.\\d{3}$";
        return Pattern.matches(fileRegex,fileName);
    }
}
