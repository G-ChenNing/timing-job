package com.github.wangchenning.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.github.wangchenning.model.Order;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyDataflowJob implements DataflowJob<Order> {

    private List<Order> orders = new ArrayList<>();

    {
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setId(i + 1);
            order.setStatus(0);
            orders.add(order);
        }
    }

    @Override
    public List<Order> fetchData(ShardingContext shardingContext) {
        List<Order> collect = orders.stream().filter(o -> o.getStatus() == 0)
                .filter(o -> o.getId() % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem())
                .collect(Collectors.toList());
        List<Order> sublist = null;
        if (collect != null && collect.size() > 0) {
            sublist = collect.subList(0, 10);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
        LocalTime time = LocalTime.now();
        System.out.println(time+",我是分片项:"+shardingContext.getShardingItem()+"，我抓取的数据"+sublist);
        return sublist;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Order> list) {
        list.forEach(o -> o.setStatus(1));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        LocalTime time = LocalTime.now();
        System.out.println(time + ",我是分片项：" + shardingContext.getShardingItem() + "我正在处理数据:" + list);
    }
}
