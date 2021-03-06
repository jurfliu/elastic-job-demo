package com.ljf.elastic.job.db.task;

import static org.junit.Assert.assertTrue;


import com.ljf.elastic.job.db.task.model.FileData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void testGenerateTestData(){
        //清除数据
        clearTestFiles();
        //制造数据
        generateTestFiles();
    }

    /**
     * 清除模拟数据
     */
    public void clearTestFiles(){
        jdbcTemplate.update("delete from t_file");
    }

    /**
     * 创建模拟数据
     */
    public void generateTestFiles(){
        List<FileData> files =new ArrayList<>();
        for(int i=1;i<11;i++){
            files.add(new FileData(String.valueOf(i),"文件"+ i,"text","content"+ i));
            files.add(new FileData(String.valueOf((i+10)),"文件"+(i+10),"image","content"+ (i+10)));
            files.add(new FileData(String.valueOf((i+20)),"文件"+(i+20),"radio","content"+ (i+20)));
            files.add(new FileData(String.valueOf((i+30)),"文件"+(i+30),"vedio","content"+ (i+30)));
        }
        for(FileData file : files){
            jdbcTemplate.update("insert into t_file (id,name,type,content,backedUp) values (?,?,?,?,?)",
                    new Object[]{file.getId(),file.getName(),file.getType(),file.getContent(),file.getBackedUp()});
        }
    }
}
