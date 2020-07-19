package com.ljf.elastic.job.db.task.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.ljf.elastic.job.db.task.model.FileData;
import com.ljf.elastic.job.db.task.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 此任务在每次执行时获取一定数目的文件，进行备份处理，由File实体类的backedUp属性来标识该文件是否已备
 份
 */
@Component
public class FileBackUpDbJob implements SimpleJob {

    //每次任务执行要备份文件的数量
    private final int FETCH_SIZE = 1;
    @Autowired
    FileService fileService;

    //任务执行代码逻辑
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            System.out.println("休眠一分钟......");
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("作业分片："+shardingContext.getShardingItem());
        //分片参数，（0=text,1=image,2=radio,3=vedio，参数就是text、image...）
        String jobParameter = shardingContext.getShardingParameter();
        //获取未备份的文件
        List<FileData> fileCustoms = fetchUnBackupFiles(jobParameter,FETCH_SIZE);
        //进行文件备份
        backupFiles(fileCustoms);
    }

    /**
     * 获取未备份的文件
     * @param count   文件数量
     * @return
     */
    public List<FileData> fetchUnBackupFiles(String fileType,int count){

        List<FileData> fileCustoms = fileService.fetchUnBackupFiles(fileType, count);
        System.out.printf("time:%s,获取文件%d个\n", LocalDateTime.now(),fileCustoms.size());
        return fileCustoms;

    }

    /**
     * 文件备份
     * @param files
     */
    public void backupFiles(List<FileData> files){
        fileService.backupFiles(files);
    }

}
