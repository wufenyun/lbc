package com.lbc.maintenance;

import com.lbc.Monitor;

/**
 * @description: 本地缓存状态信息监控器
 * @author: wufenyun
 * @date: 2018-06-28 17
 **/
public interface StatusMonitor extends Monitor {

    StatusInfo getStatusInfo();

    void logPrinting();


}
