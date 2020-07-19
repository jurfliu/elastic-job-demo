package com.ljf.elastic.job.simple.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 第4种调度方式：使用qurats
 */
public class QurartsTask {
    public static void main(String args[]) throws SchedulerException {
    //创建一个sceduler
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        Scheduler scheduler=schedulerFactory.getScheduler();
        //创建jobDetail
        JobBuilder jobBuilder=JobBuilder.newJob(MyJob.class);
        jobBuilder.withIdentity("jobname","nnd");
        JobDetail jobDetail=jobBuilder.build();
        //创建除非cronTrigger，支持按日历调度
        CronTrigger trigger=TriggerBuilder.newTrigger().withIdentity("mb","nd")
                .startNow().withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")).build();//每2秒执行一次
        //创建间隔调度
        /**
        SimpleTrigger simpleTrigger=TriggerBuilder.newTrigger().withIdentity("aaa","bbb")
                .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
                .build();
         **/
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();





    }
}
