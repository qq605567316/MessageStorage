package com.tt.msg.job;


import com.tt.msg.entity.Record;
import com.tt.msg.entity.SurfaceObservation;
import com.tt.msg.service.RadarService;
import com.tt.msg.service.RecordService;
import com.tt.msg.service.SurfaceObservationService;
import com.tt.msg.utils.ApplicationContextHelper;
import com.tt.msg.utils.DateString;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName FirstJob
 * @Description 用于处理第一种类型报文 地面观测站报文
 * @Author tanjiang
 * @CreateTime 2019/2/25 21:59
 * @Version 1.0
  **/

@Component
public class FirstJob implements Job {

    private SurfaceObservationService surfaceObservationService = ApplicationContextHelper.getBean(SurfaceObservationService.class);
    private RecordService recordService = ApplicationContextHelper.getBean(RecordService.class);

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    /**
     * 解析成功的路径
     */
    private final String successPath = File.separator + "type_one_success" + File.separator;

    /**
     * 解析失败的路径
     */
    private final String failPath = File.separator + "type_one_fail" + File.separator;

    /**
     * 报文文件结束符
     */
    private final String FILE_END_SIGN = "NNNN";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String filePath = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("filePath");
        Long timerSeq = (Long) jobExecutionContext.getJobDetail().getJobDataMap().get("timerSeq");
        File dir = new File(filePath);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return name.startsWith("Z_SURF_");
            }
        };
        //获取所有同类文件
        File[] files = dir.listFiles(fileFilter);
        if(files == null){
            return;
        }
        //每个文件单独处理
        for (File file : files) {
            this.dealFile(file, filePath, timerSeq);
        }
    }

    /**
     * 具体的处理方法
     * @param file
     */
    private void dealFile(File file, String filePath, Long timerSeq){
        //成功文件夹
        File successFile = new File(filePath + successPath + DateString.getFileDefaultString(new Date()) + "_" + file.getName());
        //获取处理成功时文件的路径
        String sucPath = successFile.getAbsolutePath();
        //失败文件夹
        File failFile = new File(filePath + failPath + DateString.getFileDefaultString(new Date()) + "_" + file.getName());
        //获取处理失败时文件的路径
        String failPath = failFile.getAbsolutePath();
        try {
            List<String> lines = FileUtils.readLines(file);
            List<SurfaceObservation> surfaceObservations = new ArrayList<SurfaceObservation>();
            int i = 0;
            do {
                SurfaceObservation surfaceObservation = new SurfaceObservation();
                surfaceObservation.setSi(lines.get(i++));
                surfaceObservation.setPp(lines.get(i++));
                surfaceObservation.setTh(lines.get(i++));
                surfaceObservation.setRe(lines.get(i++));
                surfaceObservation.setWi(lines.get(i++));
                surfaceObservation.setDt(lines.get(i++));
                surfaceObservation.setVv(lines.get(i++));
                surfaceObservation.setCw(lines.get(i++));
                surfaceObservation.setSp(lines.get(i++));
                surfaceObservation.setMr(lines.get(i++));
                surfaceObservation.setMw(lines.get(i++));
                surfaceObservation.setQc(lines.get(i++));
                surfaceObservation.setQ1(lines.get(i++));
                surfaceObservation.setQ2(lines.get(i++));
                surfaceObservation.setQ3(lines.get(i++));
                surfaceObservations.add(surfaceObservation);
            }while (!FILE_END_SIGN.equals(lines.get(i)));

            for(SurfaceObservation surfaceObservation : surfaceObservations){
                String result = surfaceObservationService.insert(surfaceObservation);
                if("s".equals(result)){
                    Long sucSeq = surfaceObservation.getSeq();
                    Record record = new Record(timerSeq, sucPath, "0", sucSeq);
                    recordService.insert(record);
                }else {
                    Record record = new Record(timerSeq, failPath,"0", result);
                    recordService.insert(record);
                }
            }
            //放入类型一解析成功文件夹
            FileUtils.moveFile(file,successFile);
        } catch (IOException e) {
            try {
                Record record = new Record(timerSeq, failPath,"0",e.getMessage());
                recordService.insert(record);
                //放入类型一解析失败文件夹
                FileUtils.moveFile(file,failFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
