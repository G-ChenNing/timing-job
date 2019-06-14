package com.github.wangchenning.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("我是分片项：" + shardingContext.getShardingItem() + ",总分片书：" + shardingContext.getShardingTotalCount());

    }
}
