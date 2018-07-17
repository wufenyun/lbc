package com.lbc.context.event;

import com.lbc.maintenance.StatusInfo;

/**
 * @description: 本地缓存刷新事件
 * @author: wufenyun
 * @date: 2018-07-10 12
 **/
public class CacheRefreshedEvent extends Event {


    public CacheRefreshedEvent(StatusInfo source, long time) {
        super(source, time);
    }
}