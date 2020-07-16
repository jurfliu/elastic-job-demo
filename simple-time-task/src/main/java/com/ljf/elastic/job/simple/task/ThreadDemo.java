package com.ljf.elastic.job.simple.task;

public class ThreadDemo {
    public static void main(String args[]){
        new Thread(new ThreadTask()).start();
    }
}
