package com.ljf.elastic.job.db.task.service;

import com.ljf.elastic.job.db.task.model.FileData;
import com.ljf.elastic.job.db.task.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class FileService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 获取某文件类型未备份的文件
     * @param fileType 文件类型
     * @param count 获取条数
     * @return
     */
    public List<FileData> fetchUnBackupFiles(String fileType, Integer count){
        String sql="select * from t_file where type = ? and backedUp = 0 limit 0,?";
        List<FileData> files = jdbcTemplate.query(sql, new Object[]{fileType, count}, new BeanPropertyRowMapper(FileData.class));
        return files;
    }

    /**
     * 备份文件
     * @param files 要备份的文件
     */
    public void backupFiles(List<FileData> files){
        for(FileData fileCustom:files){
            String sql="update t_file set backedUp = 1 where id = ?";
            jdbcTemplate.update(sql,new Object[]{fileCustom.getId()});
            System.out.println(String.format("time:%s,线程 %d | 已备份文件:%s  文件类型:%s"
                    , LocalDateTime.now()
                    ,Thread.currentThread().getId()
                    ,fileCustom.getName()
                    ,fileCustom.getType()));
        }

    }
}
