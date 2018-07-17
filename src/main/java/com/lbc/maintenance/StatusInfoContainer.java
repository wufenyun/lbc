package com.lbc.maintenance;

/**
 * @description: 本地缓存状态信息
 * @author: wufenyun
 * @date: 2018-06-28 17
 **/
public interface StatusInfoContainer {

    void updateStatusInfo(StatusInfo statusInfo);

    StatusInfo getStatusInfo(Object key);

    String getFullInfo();
}
