package com.lbc;

/**
 * @description: 本地缓存监控器
 * @author: wufenyun
 * @date: 2018-06-28 16
 **/
public interface Monitor {
    /**
     *开启监控功能
     */
    void startMonitoring();

    /**
     * 关闭监控器
     */
    void stopMonitor();
}
