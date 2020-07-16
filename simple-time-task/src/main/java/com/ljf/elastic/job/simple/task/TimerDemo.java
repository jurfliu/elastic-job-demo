package com.ljf.elastic.job.simple.task;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
    /**
     *   第二种方法：设定指定任务task在指定延迟delay后进行固定延迟peroid的执行
     *   意思是当程序获取执行权开始执行时，再按照dalay设置的延迟值，等待xx秒后执行，
     *   period，表示按什么周期频率去执行。
     *
     *
     *
     */

    public static void main(String args[]){
    Timer t=new Timer();
    t.schedule(new TimerTask() {
        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = simpleDateFormat.format(new Date());
            System.out.println(dateString+"定时任务");
        }
    },1000, 5000);
    }
}
