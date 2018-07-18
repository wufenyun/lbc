package com.lbc.context.event;

import java.util.Set;

/**
 * 事件消息多播器
 *
 * @author: wufenyun
 **/
public interface EventMulticaster {

    void addListener(EventListener<?> eventListener);

    void removeListener(EventListener<?> eventListener);

    void removeAllListeners();

    void multicast(Event event);

    /**
     * 通过事件类型获取订阅了该类型事件的监听器
     *
     * @param event
     * @return
     */
    Set<EventListener<?>> getEventListener(Event event);
}
