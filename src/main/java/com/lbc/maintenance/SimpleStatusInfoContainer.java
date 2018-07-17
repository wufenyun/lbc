package com.lbc.maintenance;

import com.lbc.support.AssertUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 本地缓存状态信息
 * @author: wufenyun
 * @date: 2018-06-28 17
 **/
public class SimpleStatusInfoContainer implements StatusInfoContainer {
    /**
     * 最近更新时间，等于最后进行更新的key缓存
     */
    private long lastUpdateTime;

    /**
     *所有key的更新信息
     */
    private ConcurrentHashMap<Object,StatusInfo> fullInfo = new ConcurrentHashMap<>();
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void updateStatusInfo(StatusInfo statusInfo) {
        AssertUtil.notNull(statusInfo);
        AssertUtil.notNull(statusInfo.getKey());

        lock.lock();
        try {
            lastUpdateTime = (statusInfo.getUpdateTime() == 0)?System.currentTimeMillis():statusInfo.getUpdateTime();
            if(null != statusInfo) {
                fullInfo.put(statusInfo.getKey(),statusInfo);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public StatusInfo getStatusInfo(Object key) {
        AssertUtil.notNull(key);
        return fullInfo.get(key);
    }

    @Override
    public String getFullInfo() {
        return toString();
    }

    @Override
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry : fullInfo.entrySet()) {
            stringBuffer.append(entry.toString()).append(",");
        }

        return "SimpleStatusInfoContainer{" +
                "lastUpdateTime=" + lastUpdateTime +
                ", fullInfo=" + stringBuffer.toString() +
                '}';
    }
}