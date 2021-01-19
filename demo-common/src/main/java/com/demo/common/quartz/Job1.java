package com.demo.common.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class Job1 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job1 start run time is "+ LocalDateTime.now());
        System.out.println("job1 context is "+context);
        System.out.println("job1.ctx.name = "+context.get("job1.ctx.name"));
    }
}
