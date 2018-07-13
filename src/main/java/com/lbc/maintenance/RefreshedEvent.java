package com.lbc.maintenance;

import com.lbc.context.event.Event;

/**
 * @description: 缓存刷新事件
 * @author: wufenyun
 * @date: 2018-07-10 20
 **/
public class RefreshedEvent extends Event {

    public RefreshedEvent(Object source) {
        this.source = source;
    }
}