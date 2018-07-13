package com.lbc.context.event;

import com.lbc.maintenance.UpdateInfo;

/**
 * @description: 事件生成工厂
 * @author: wufenyun
 * @date: 2018-07-11 09
 **/
public class EventFactory {

    public static CacheRefreshedEvent newRefreshedEvent(Object key,long previousCount,long currentCount) {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setKey(key);
        updateInfo.setPreviousCount(previousCount);
        updateInfo.setCurrentCount(currentCount);
        long time = System.currentTimeMillis();
        updateInfo.setUpdateTime(time);
        CacheRefreshedEvent cacheRefreshedEvent = new CacheRefreshedEvent(updateInfo,time);
        return cacheRefreshedEvent;
    }
}