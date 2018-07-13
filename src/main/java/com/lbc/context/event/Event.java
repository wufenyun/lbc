package com.lbc.context.event;

/**
 * @description: 事件对象
 * @author: wufenyun
 * @date: 2018-07-10 11
 **/
public abstract class Event<T> {

    /**
     * 事件发生时间
     */
    protected long time;

    /**
     * 需要传递对象
     */
    protected T  source;

    public Event() {
        this(null,System.currentTimeMillis());
    }

    public Event(T source) {
        this(source,System.currentTimeMillis());
    }

    public Event(T source,long time) {
        this.time = time;
        this.source = source;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

}