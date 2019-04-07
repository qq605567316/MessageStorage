package com.tt.msg.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @ClassName ThirdJob
 * @Description TODO
 * @Author tanjiang
 * @CreateTime 2019/2/25 22:07
 * @Version 1.0
 **/

public class ThirdJob implements Job {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        String filePath = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("filePath");
        System.out.println("ThirdJob===========================================" + filePath);
    }
}
