package com.lbc.maintenance;

/**
 * @description: 本地缓存状态信息监控器实现
 * @author: wufenyun
 * @date: 2018-06-28 18
 **/
public class SimpleStatusMonitor implements StatusMonitor {

    private StatusInfo statusInfo = new StatusInfo();


    @Override
    public void startMonitoring() {

    }

    @Override
    public void stopMonitor() {

    }

    @Override
    public StatusInfo getStatusInfo() {
        return null;
    }

    @Override
    public void logPrinting() {

    }
}