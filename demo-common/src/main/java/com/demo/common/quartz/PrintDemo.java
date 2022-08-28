package com.demo.common.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class PrintDemo {

    /**
     *  1:N 调度关系
     * @throws Exception
     */
    public void run1() throws Exception{
        JobDetail job1 = JobBuilder.newJob(PrintJob.class)
                .withIdentity("job1","print-group")
                .usingJobData("name","jetty")
                .build();
        JobDetail job2 = JobBuilder.newJob(PrintJob.class)
                .withIdentity("job2","print-group")
                .usingJobData("name","jack")
                .build();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1-print","group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2).repeatForever()
                )
                .build();
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2-print","group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10).repeatForever()
                )
                .build();
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();
        scheduler.scheduleJob(job1,trigger1);
        scheduler.scheduleJob(job2,trigger2);
        scheduler.start();
        Thread.sleep(1000*60);
        scheduler.shutdown();
    }


    public void run2() throws Exception{

        JobDetail job1 = JobBuilder.newJob(PrintJob.class)
                .withIdentity("job1","print-group")
                .usingJobData("name","jetty")
                .storeDurably()
                .build();

        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1-print","group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2).repeatForever()
                )
                .forJob(job1)
                .build();

        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2-print","group1")
                .startNow()
                .forJob(job1)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10).repeatForever()
                )
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.addJob(job1,false);
        scheduler.scheduleJob(trigger1);
        scheduler.scheduleJob(trigger2);
        scheduler.startDelayed(10);
        //scheduler.start();

    }

    public void run3() throws Exception{
        JobDetail job1 = JobBuilder.newJob(PrintJob.class)
                .withIdentity("job1","print-group")
                .usingJobData("name","jetty")
                .storeDurably()
                .build();
        JobDetail job2 = JobBuilder.newJob(PrintJob.class)
                .withIdentity("job2","print-group")
                .usingJobData("name","jack")
                .build();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1-print","group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2).repeatForever()
                )
                .forJob(job1)
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        Thread.sleep(2000);
        scheduler.addJob(job1,false);
        scheduler.scheduleJob(trigger1);
        Thread.sleep(10000);
        scheduler.pauseAll();
        System.out.println("暂停所有任务...");
        Thread.sleep(10000);
        scheduler.resumeAll();
        System.out.println("启动所有任务...");
        Thread.sleep(10000);
        scheduler.shutdown();
        System.out.println("结束所有任务...");
        //scheduler.start();
        //scheduler.triggerJob(JobKey.jobKey("job1","print-group"));
        //scheduler.resumeAll();
    }

    public static void main(String[] args) {
        try {
            PrintDemo demo = new PrintDemo();
            //demo.run1();
            //demo.run2();
            demo.run3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
