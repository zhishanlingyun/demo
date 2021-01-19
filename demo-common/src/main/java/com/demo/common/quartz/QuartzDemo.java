package com.demo.common.quartz;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzDemo {

    public static void main(String[] args) throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Trigger tg1 = TriggerBuilder.newTrigger().build();

    }

}
