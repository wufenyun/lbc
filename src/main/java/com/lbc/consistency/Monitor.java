package com.lbc.consistency;

/**
 * 本地缓存数据一致性监控器
 *
 * @author: wufenyun
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
