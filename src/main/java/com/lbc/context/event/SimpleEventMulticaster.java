package com.lbc.context.event;

import com.lbc.support.AssertUtil;
import com.lbc.support.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EventMulticaster接口实现类。
 * 广播消息时通过事件类型获取订阅了该类型事件的监听器，然后执行传播事件
 *
 *
 * @author: wufenyun
 **/
public class SimpleEventMulticaster implements EventMulticaster {

    private final static Logger logger = LoggerFactory.getLogger(SimpleEventMulticaster.class);

    private Map<String,Set<EventListener<?>>> listenersGroup = new ConcurrentHashMap<>(64);

    @Override
    public void addListener(EventListener<?> eventListener) {
        AssertUtil.notNull(eventListener);
        Type eventType = BeanUtil.getGenericTypeOnSupperInterface(eventListener);
        AssertUtil.notNull(eventType);

        Set<EventListener<?>> set = listenersGroup.get(eventType.getTypeName());
        synchronized (listenersGroup) {
            if (null == set) {
                set = new HashSet<>();
                set.add(eventListener);
                listenersGroup.put(eventType.getTypeName(),set);
            } else {
                set.add(eventListener);
            }
        }
    }

    @Override
    public void removeListener(EventListener<?> eventListener) {
        AssertUtil.notNull(eventListener);
        Type eventType = BeanUtil.getGenericTypeOnSupperInterface(eventListener);
        AssertUtil.notNull(eventType);

        Set<EventListener<?>> set = listenersGroup.get(eventType.getTypeName());
        synchronized (listenersGroup) {
            if (null != set) {
                set.clear();
                listenersGroup.remove(eventType.getTypeName());
            }
        }
    }

    @Override
    public void removeAllListeners() {
        listenersGroup.clear();
    }

    @Override
    public void multicast(Event event) {
        Set<EventListener<?>> listeners = getEventListener(event);
        listeners.forEach((eventListener -> {
            try {
                eventListener.onEvent(event);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }));
    }

    @Override
    public Set<EventListener<?>> getEventListener(Event event) {
        return listenersGroup.get(event.getClass().getTypeName());
    }
}