package com.demo.common.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

/**
 * 依赖
 * 配置
 * 调度器
 * 任务
 * 触发器
 */
public class QuartzDemo {


    public void job1() throws Exception{
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        Trigger tg = TriggerBuilder.newTrigger()
                //.forJob("job1")
                .withIdentity("tg1","tg")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                .withIntervalInSeconds(6)
                .repeatForever())
                .startNow()
                .build();
        JobKey jk = new JobKey("job1","tc");
        JobDetail jd = JobBuilder
                .newJob(Job1.class)
                .withIdentity(jk)
                .build();
        /*scheduler.scheduleJob(tg);
        scheduler.addJob(jd,false);*/
        scheduler.scheduleJob(jd,tg);
        scheduler.start();
        System.out.println(scheduler.checkExists(new JobKey("job1","tc")));
        System.out.println(scheduler.checkExists(new JobKey("job1","")));
        Thread.sleep(10*1000);
        List<JobExecutionContext> executingJobs =
                scheduler.getCurrentlyExecutingJobs();
        System.out.println("exe current "+executingJobs);
        scheduler.pauseJob(jk);
        System.out.println("------- pause------");

        Thread.sleep(30*1000);
        scheduler.resumeJob(jk);
        System.out.println("-------- resume ---------");
    }

    public void job2() throws Exception{
        JobKey jk1 = new JobKey("job-1","jp");
        JobKey jk2 = new JobKey("job-2","jp");
        JobDetail job1 = JobBuilder
                .newJob(Job1.class)
                .withIdentity("job-1","jp")
                .storeDurably()
                .withDescription("job1")
                .build();
        JobDetail job2 = JobBuilder
                .newJob(Job2.class)
                .withIdentity("job-2","jp")
                .withDescription("job2")
                .build();
        Trigger t1 = TriggerBuilder.
                <SimpleTrigger>newTrigger()
                .withIdentity("trigger-1","gp")
                .forJob(jk1)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(2))
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //scheduler.scheduleJob(job1,t1);
        //scheduler.scheduleJob(job2,t1);
        //scheduler.addJob(job1,false);
        scheduler.scheduleJob(t1);
        scheduler.triggerJob(jk1);

        scheduler.start();


    }

    public static void main(String[] args) throws Exception{

        QuartzDemo demo = new QuartzDemo();
        demo.job2();



    }

}
