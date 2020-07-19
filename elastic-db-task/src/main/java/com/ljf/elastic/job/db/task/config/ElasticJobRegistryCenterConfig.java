package com.ljf.elastic.job.db.task.config;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticJobRegistryCenterConfig {

    //zookeeper链接字符串 localhost:2181
    private  String ZOOKEEPER_CONNECTION_STRING = "127.0.0.1:2181" ;
    //定时任务命名空间
    private  String JOB_NAMESPACE = "elastic-job-task-java";

    //zk的配置及创建注册中心
    @Bean(initMethod = "init")
    public CoordinatorRegistryCenter setUpRegistryCenter(){
        System.out.println("正在初始化zk....");
        //zk的配置
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);

        zookeeperConfiguration.setSessionTimeoutMilliseconds(1000);
        //创建注册中心
        CoordinatorRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        return zookeeperRegistryCenter;

    }
}
