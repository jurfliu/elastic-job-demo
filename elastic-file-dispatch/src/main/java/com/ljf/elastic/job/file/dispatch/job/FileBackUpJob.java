package com.ljf.elastic.job.file.dispatch.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ljf.elastic.job.file.dispatch.model.FileData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 此任务在每次执行时获取一定数目的文件，进行备份处理，由File实体类的backedUp属性来标识该文件是否已备
 份
 */
public class FileBackUpJob implements SimpleJob {
    //每次任务执行要备份文件的数量
    private final int FETCH_SIZE = 1;
    //文件列表（模拟）
    public static List<FileData> filesList = new ArrayList<FileData>();
    //任务执行代码逻辑
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("作业分片："+shardingContext.getShardingItem());
        //获取未备份的文件
        List<FileData> fileCustoms = fetchUnBackupFiles(FETCH_SIZE);
        //进行文件备份
        backupFiles(fileCustoms);
        System.out.println("============");
    }
    /**
     * 获取未备份的文件
     * @param count   文件数量
     * @return
     */
    public List<FileData> fetchUnBackupFiles(int count){

        //获取的文件列表
        List<FileData> fileCustoms = new ArrayList<>();
        int num=0;
        for(FileData fileCustom:filesList){
            if(num >=count){
                break;
            }
            if(!fileCustom.getBackedUp()){
                fileCustoms.add(fileCustom);
                num ++;
            }

        }
        System.out.printf("time:%s,获取文件%d个\n", LocalDateTime.now(),num);
        return fileCustoms;

    }
    /**
     * 文件备份
     * @param files
     */
    public void backupFiles(List<FileData> files){
        for(FileData fileCustom:files){
            fileCustom.setBackedUp(true);
            System.out.printf(Thread.currentThread().getName()+" time:%s,备份文件，名称：%s，类型：%s\n", LocalDateTime.now(),fileCustom.getName(),fileCustom.getType());
        }
        List<FileData> collect = filesList.stream().filter(u -> u.getBackedUp()==false).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName()+":还剩余:"+collect.size()+"个文件");

    }

}
