package com.lbc.consistency.eventdriven.support;

import com.lbc.support.AssertUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建并注册单例ZkCacheStatusChager
 *
 * @author: wufenyun
 **/
public final class SingletonZKChangerRegistry {

    private final Map<String,ZkCacheStatusChanger> changerMap = new ConcurrentHashMap<>();

    public ZkCacheStatusChanger createOrGetChanger(String zkConnection,String refresherDataNodeName) {
        AssertUtil.notBlank(zkConnection,"zookeeper地址不能为空");

        String key = refresherDataNodeName + zkConnection;
        if(null != changerMap.get(key)) {
            return changerMap.get(key);
        } else {
            ZkCacheStatusChanger changer = new ZkCacheStatusChanger(zkConnection, "rms");
            changerMap.put(key,changer);
            return changer;
        }
    }
}