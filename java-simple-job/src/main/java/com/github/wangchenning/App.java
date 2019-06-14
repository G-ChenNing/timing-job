package com.github.wangchenning;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.github.wangchenning.job.MySimpleJob;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        new JobScheduler(zkCenter(), configuration()).init();
    }

    /**
     * 注册中心
     * @return
     */
    public static CoordinatorRegistryCenter zkCenter() {
        ZookeeperConfiguration zkc = new ZookeeperConfiguration("cl0505.club:2181", "java-simple-job2");
        //ip:port,ip:port,ip:port
        CoordinatorRegistryCenter crc = new ZookeeperRegistryCenter(zkc);
        crc.init();
        return crc;
    }

    /*
     *JOB配置
     */
    public static LiteJobConfiguration configuration() {
        //job核心配置
        JobCoreConfiguration jcc = JobCoreConfiguration.newBuilder("mySimpleJob", "0/10 * * * * ?", 2).build();
        //job类型配置
        JobTypeConfiguration jtc = new SimpleJobConfiguration(jcc, MySimpleJob.class.getCanonicalName());
        //job根的配置(LiteJobConfiguration)
        LiteJobConfiguration ljc = LiteJobConfiguration.newBuilder(jtc).build();
        return ljc;
    }
}
