package io.daiwei.shardingjdbc.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.*;

/**
 * 数据库切分逻辑
 * Created by Daiwei on 2021/3/13
 */
public class DSShardingAlgo implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        Long value = shardingValue.getValue();
        List<String> availableDs = new ArrayList<>(availableTargetNames);
        return availableDs.get((int) (value / 18) % availableDs.size());
    }
}
