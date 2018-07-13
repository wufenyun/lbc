package com.lbc.maintenance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 本地缓存状态信息
 * @author: wufenyun
 * @date: 2018-06-28 17
 **/
public class StatusComponent {
    /**
     * 最近更新时间，等于最后进行更新的key缓存
     */
    private long lastUpdateTime;

    /**
     *所有key的更新信息
     */
    private ConcurrentHashMap<Object,UpdateInfo> fullInfo = new ConcurrentHashMap<>();

    public void update(Object key,UpdateInfo info) {
        if(null != info) {
            fullInfo.put(key,info);
        }
    }

    @Override
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry : fullInfo.entrySet()) {
            stringBuffer.append(entry.toString()).append(",");
        }

        return "StatusComponent{" +
                "lastUpdateTime=" + lastUpdateTime +
                ", fullInfo=" + stringBuffer.toString() +
                '}';
    }




}