package com.ljf.elastic.job.simple.task;

import java.util.Date;

public class ThreadTask  implements Runnable {
    @Override
    public void run() {
        while(true) {
            System.out.println(new Date().getTime()+"定时任务");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
