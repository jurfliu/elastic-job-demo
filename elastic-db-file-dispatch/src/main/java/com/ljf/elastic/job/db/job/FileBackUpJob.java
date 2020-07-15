package com.ljf.elastic.job.db.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ljf.elastic.job.db.model.FileData;
import com.ljf.elastic.job.db.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileBackUpJob implements SimpleJob {
    //每次任务执行要备份文件的数量
    private final int FETCH_SIZE = 1;
    //文件列表（模拟）
    public static List<FileData> files = new ArrayList<>();

    static {
        for(int i=1;i<11;i++){
            System.out.println("正常生产数据，第:。。。。"+i+"批");
            FileBackUpJob.files.add(new FileData(String.valueOf(i+10),"文件"+(i+10),"text","content"+ (i+10)));
            FileBackUpJob.files.add(new FileData(String.valueOf(i+20),"文件"+(i+20),"image","content"+ (i+20)));
            FileBackUpJob.files.add(new FileData(String.valueOf(i+30),"文件"+(i+30),"radio","content"+ (i+30)));
            FileBackUpJob.files.add(new FileData(String.valueOf(i+40),"文件"+(i+40),"video","content"+ (i+40)));
        }
        System.out.println("生产测试数据完成,数据大小为:"+FileBackUpJob.files.size());
    }

    //任务执行代码逻辑
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("作业分片："+shardingContext.getShardingItem());
        //分片参数，（0=text,1=image,2=radio,3=vedio，参数就是text、image...）
        String jobParameter = shardingContext.getJobParameter();

        //获取未备份的文件
        List<FileData> fileCustoms = fetchUnBackupFiles(FETCH_SIZE);
        //进行文件备份
        backupFiles(fileCustoms);
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
        for(FileData fileCustom:files){
            if(num >=count){
                break;
            }
            if(!fileCustom.getBackedUp()){
                fileCustoms.add(fileCustom);
                num ++;
            }

        }
        System.out.printf(Thread.currentThread().getName()+"文件状态:"+((fileCustoms!=null&&fileCustoms.size()>0)?fileCustoms.get(0).getBackedUp():0)+"  time:%s,获取文件%d个\n", LocalDateTime.now(),num);
        return fileCustoms;

    }

    /**
     * 文件备份
     * @param filesList
     */
    public void backupFiles(List<FileData> filesList){
        for(FileData fileCustomm:filesList){
            System.out.printf(Thread.currentThread().getName()+"备份之前的状态:"+fileCustomm.getBackedUp()+" time:%s,备份文件，名称：%s，类型：%s\n", LocalDateTime.now(),fileCustomm.getName(),fileCustomm.getType());
            fileCustomm.setBackedUp(true);
            System.out.printf(Thread.currentThread().getName()+" time:%s,备份文件，名称：%s，类型：%s\n", LocalDateTime.now(),fileCustomm.getName(),fileCustomm.getType());
        }
        List<FileData> collect = files.stream().filter(u -> u.getBackedUp()==false).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName()+" time:"+LocalDateTime.now()+",  还剩余:"+collect.size()+"个文件");
    }
}
