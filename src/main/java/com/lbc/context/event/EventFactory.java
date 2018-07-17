package com.lbc.context.event;

import com.lbc.maintenance.StatusInfo;

/**
 * @description: 事件生成工厂
 * @author: wufenyun
 * @date: 2018-07-11 09
 **/
public class EventFactory {

    public static CacheRefreshedEvent newRefreshedEvent(Object key,long previousCount,long currentCount) {
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setKey(key);
        statusInfo.setPreviousCount(previousCount);
        statusInfo.setCurrentCount(currentCount);
        long time = System.currentTimeMillis();
        statusInfo.setUpdateTime(time);
        CacheRefreshedEvent cacheRefreshedEvent = new CacheRefreshedEvent(statusInfo,time);
        return cacheRefreshedEvent;
    }
}