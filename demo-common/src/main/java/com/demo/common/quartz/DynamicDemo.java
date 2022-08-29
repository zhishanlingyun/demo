package com.demo.common.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;
import java.util.Optional;

public class DynamicDemo {

    /**
     * 动态更新同名(name-group)的job
     * @throws Exception
     */
    public void run1() throws Exception{

        JobDetail fb = JobBuilder.newJob()
                .ofType(PrintJob.class)
                .withIdentity("fb","g1")
                .usingJobData("name","fb-dym")
                .storeDurably()
                .build();

        JobDetail job = JobBuilder.newJob()
                .ofType(Job2.class)
                .withIdentity("fb","g1")
                .usingJobData("name","dym")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1).repeatForever())
                //.forJob(job)
                .build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(fb,trigger);
        scheduler.start();
        Thread.sleep(10000);
        System.out.println(">>>>>> update job ...");
        scheduler.addJob(job,true);
        Thread.sleep(10000);
        System.out.println(">>>>>> update job ...");
        scheduler.addJob(fb,true);

    }

    public void run2() throws Exception{
        JobDetail job = JobBuilder.newJob()
                .ofType(PrintJob.class)
                .withIdentity("fb","g1")
                .usingJobData("name","dym")
                .build();
        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("t1","g1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1).repeatForever())
                .build();
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("t2","g1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(6).repeatForever())
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(job,trigger1);
        scheduler.start();
        Thread.sleep(10000);
        System.out.println(">>>>>> 更新trigger ...");
        scheduler.rescheduleJob(TriggerKey.triggerKey("t1","g1"),trigger2);
    }

    public void run3() throws Exception{
        JobDetail job1 = JobBuilder.newJob(PrintJob.class).storeDurably().withIdentity("job1","g1").build();
        JobDetail job2 = JobBuilder.newJob(PrintJob.class).storeDurably().withIdentity("job2","g1").build();
        JobDetail job3 = JobBuilder.newJob(PrintJob.class).storeDurably().withIdentity("job3","g2").build();

        Trigger trigger1 = TriggerBuilder.newTrigger()
                .forJob(job1)
                .withIdentity("t1","g1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(6).repeatForever())
                .build();
        Trigger trigger2 = TriggerBuilder.newTrigger()
                .forJob(job2)
                .withIdentity("t2","g1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(6).repeatForever())
                .build();
        Trigger trigger3 = TriggerBuilder.newTrigger()
                .forJob(job3)
                .withIdentity("t3","g2")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(6).repeatForever())
                .build();
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.addJob(job1,false);
        scheduler.addJob(job2,false);
        scheduler.addJob(job3,false);
        scheduler.scheduleJob(trigger1);
        scheduler.scheduleJob(trigger2);
        scheduler.scheduleJob(trigger3);
        //scheduler.start();
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(JobKey.jobKey("job1","g1"));
        Optional.ofNullable(triggers).ifPresent(System.out::println);
    }

    public static void main(String[] args) {
        try {
            DynamicDemo demo = new DynamicDemo();
            //demo.run1();
            //demo.run2();
            demo.run3();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
