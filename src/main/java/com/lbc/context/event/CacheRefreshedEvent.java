package com.lbc.context.event;

/**
 * @description: 本地缓存刷新事件
 * @author: wufenyun
 * @date: 2018-07-10 12
 **/
public class CacheRefreshedEvent<UpdateInfo> extends Event {


    public CacheRefreshedEvent(UpdateInfo source, long time) {
        super(source, time);
    }
}