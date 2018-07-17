package com.lbc.context.event;

/**
 * @description: 事件监听器
 * @author: wufenyun
 * @date: 2018-07-10 11
 **/
public interface EventListener<E extends Event> {

    void onEvent(Event event);
}
