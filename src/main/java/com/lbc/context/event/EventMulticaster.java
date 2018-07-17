package com.lbc.context.event;

import java.util.Set;

/**
 * @description: 事件消息多播器
 * @author: wufenyun
 * @date: 2018-07-10 11
 **/
public interface EventMulticaster {

    void addListener(EventListener<?> eventListener);

    void removeListener(EventListener<?> eventListener);

    void removeAllListeners();

    void multicast(Event event);

    Set<EventListener<?>> getEventListener(Event event);
}
