package com.ljf.elastic.job.simple.task;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用第3种方式：使用周期性线程池调度ScheduledExecutorService
 */
public class ThreadPoolSchedule {
    public static void main(String args[]){
        ScheduledExecutorService es= Executors.newScheduledThreadPool(10);
        es.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
            System.out.println(Thread.currentThread().getName()+" "+ LocalDateTime.now()+"do somethings");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },5,2, TimeUnit.SECONDS);
    }
}
