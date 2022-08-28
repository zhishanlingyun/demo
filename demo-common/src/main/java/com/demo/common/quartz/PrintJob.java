package com.demo.common.quartz;

import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrintJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:dd:ss"));
        String msg = String.format("[%s] , name = %s", now,jobDataMap.getString("name"));
        System.out.println(msg);
    }
}
