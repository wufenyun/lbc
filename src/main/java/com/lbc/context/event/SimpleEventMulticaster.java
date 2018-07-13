package com.lbc.context.event;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @description: EventMulticaster实现类
 * @author: wufenyun
 * @date: 2018-07-10 11
 **/
public class SimpleEventMulticaster implements EventMulticaster {

    private Set<EventListener> listeners = new LinkedHashSet<>();

    @Override
    public void addListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public void removeListener(EventListener eventListener) {
        listeners.remove(eventListener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public void multicast(Event event) {
        listeners.forEach((eventListener -> {
            eventListener.onEvent(event);
        }));
    }
}