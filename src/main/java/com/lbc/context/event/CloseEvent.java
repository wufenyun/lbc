package com.lbc.context.event;

/**
 * @description: 关闭本地缓存事件
 * @author: wufenyun
 * @date: 2018-07-11 11
 **/
public class CloseEvent extends Event {

    public CloseEvent(){

    }

    public CloseEvent(Object source) {
        super(source);
    }
}