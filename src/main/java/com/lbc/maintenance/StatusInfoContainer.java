package com.lbc.maintenance;

/**
 * 本地缓存状态信息维护组件接口
 *
 * @author: wufenyun
 **/
public interface StatusInfoContainer {

    /**
     * 更新指定key的状态信息
     * @param statusInfo
     */
    void updateStatusInfo(StatusInfo statusInfo);

    /**
     * 获取指定key的状态信息
     * @param key
     * @return
     */
    StatusInfo getStatusInfo(Object key);

    /**
     * 获取全量缓存状态信息
     * @return
     */
    String getFullInfo();
}
