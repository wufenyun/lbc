/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:49:13
 * @author wufenyun 
 */
public class CacheConfiguration {
    
    /**
     * 定时刷新缓存任务初次执行延迟时间，默认立即执行
     */
    private long initialDelay = 0;
    /**
     * 定时刷新缓存任务执行间隔时间，默认1000毫秒
     */
    private long intervalMills = 1000;
    /**
     * 定时刷新缓存任务线程数，默认单线程
     */
    private int refreshThreads = 1;
    
    private MonitorModel monitorModel = MonitorModel.POLLING;
    
    /**
     * zookeeper地址
     */
    private String zkConnection;
    private String rootPath = "/localBatchCache";
    
    public static enum MonitorModel {
        /**
         * 采用轮询的模式来监控缓存是否需要刷新
         */
        POLLING,
        /**
         * 借助zookeeper实现事件驱动模式来监控缓存是否需要刷新
         */
        EVNET_ZK;
    }
    
    public long getIntervalMills() {
        return intervalMills;
    }

    public void setIntervalMills(long intervalMills) {
        this.intervalMills = intervalMills;
    }

    public int getRefreshThreads() {
        return refreshThreads;
    }

    public void setRefreshThreads(int refreshThreads) {
        this.refreshThreads = refreshThreads;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public MonitorModel getMonitorModel() {
        return monitorModel;
    }

    public void setMonitorModel(MonitorModel monitorModel) {
        this.monitorModel = monitorModel;
    }

    public String getZkConnection() {
        return zkConnection;
    }

    public void setZkConnection(String zkConnection) {
        this.zkConnection = zkConnection;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

}
