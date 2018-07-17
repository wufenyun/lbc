package com.lbc.context.event;

/**
 * @description: 事件对象
 * @author: wufenyun
 * @date: 2018-07-10 11
 **/
public abstract class Event {

    /**
     * 事件发生时间
     */
    protected long time;

    /**
     * 需要传递对象
     */
    protected Object source;

    public Event() {
        this(null,System.currentTimeMillis());
    }

    public Event(Object source) {
        this(source,System.currentTimeMillis());
    }

    public Event(Object source,long time) {
        this.time = time;
        this.source = source;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

}