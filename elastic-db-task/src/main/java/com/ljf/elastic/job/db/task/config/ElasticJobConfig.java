package com.ljf.elastic.job.db.task.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.ljf.elastic.job.db.task.job.FileBackUpDbJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ElasticJobConfig {
    @Autowired
    private DataSource dataSource; //数据源已经存在，直接引入
    @Autowired
    FileBackUpDbJob fileBackupJob;
    @Autowired
    CoordinatorRegistryCenter registryCenter;
    /**
     * 配置任务详细信息
     * @param jobClass 任务执行类
     * @param cron  执行策略
     * @param shardingTotalCount 分片数量
     * @param shardingItemParameters 分片个性化参数
     * @return
     */
    private LiteJobConfiguration createJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                        final String cron,
                                                        final int shardingTotalCount,
                                                        final String shardingItemParameters){
        //JobCoreConfigurationBuilder
        JobCoreConfiguration.Builder JobCoreConfigurationBuilder = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount);
        //设置shardingItemParameters
        if(!StringUtils.isEmpty(shardingItemParameters)){
            JobCoreConfigurationBuilder.shardingItemParameters(shardingItemParameters);
        }
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfigurationBuilder.build();
        //创建SimpleJobConfiguration
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName());
        //创建LiteJobConfiguration
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true)
               // .monitorPort(9888)//设置dump端口,多个窗口执行后，提示jvm已经绑定。

                .build();
        return liteJobConfiguration;
    }
    @Bean(initMethod = "init")
    public SpringJobScheduler initSimpleElasticJob() {
        // 增加任务事件追踪配置
        System.out.println("..........");
        // 增加任务事件追踪配置 ，打开就会在数据库中新建job_execution_log和job_status_trace_log这两个表
        JobEventConfiguration jobEventConfig = new JobEventRdbConfiguration(dataSource);
        //创建SpringJobScheduler

        SpringJobScheduler springJobScheduler = new SpringJobScheduler(fileBackupJob, registryCenter,
                //"0=text,1=image,2=radio,3=vedio"   设置不同分片，查询不同类型的任务，分片的设置，避免不同实例查询相同类型的文件
                createJobConfiguration(fileBackupJob.getClass(), "0/10 * * * * ?", 4, "0=text,1=image,2=radio,3=vedio")
                ,jobEventConfig);
        return springJobScheduler;
    }
}
