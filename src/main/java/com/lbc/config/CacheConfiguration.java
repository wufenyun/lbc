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
    private long initialDelay = Constant.DEFAULT_INITIAL_DELAY;
    /**
     * 定时刷新缓存任务执行间隔时间，默认1000毫秒
     */
    private long intervalMills = Constant.DEFAULT_INTERVAL_MILLS;
    /**
     * 定时刷新缓存任务线程数，默认单线程
     */
    private int refreshThreads = Constant.DEFAULT_REFRESH_THREADS;
    
    
    /**
     * 缓存最大阈值，按key的多少计算，默认-1(即不限制缓存数量)，用户可以自己设定
     */
    private int cacheSizeThreshold = Constant.DEFAULT_CACHESIZE_THRESHOLD;
    
    private MonitorModel monitorModel = MonitorModel.POLLING;
    /**
     * zookeeper地址
     */
    private String zkConnection;
    /**
     * 应用专属zk数据节点名，用以EVNET_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
     */
    private String yourZkDataNode = Constant.DEFAULT_ZKDATA_NODENAME;
    
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
    
    public static class Constant {
        public final static int DEFAULT_INITIAL_DELAY = 0;
        public final static int DEFAULT_INTERVAL_MILLS = 1000;
        public final static int DEFAULT_REFRESH_THREADS = 1;
        public final static int DEFAULT_CACHESIZE_THRESHOLD = -1;
        public final static String ROOTPATH = "/localBatchCache";
        public final static String DEFAULT_ZKDATA_NODENAME = "/defaultRefresherDataNode";
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

    public int getCacheSizeThreshold() {
        return cacheSizeThreshold;
    }

    public void setCacheSizeThreshold(int cacheSizeThreshold) {
        this.cacheSizeThreshold = cacheSizeThreshold;
    }

    public String getYourZkDataNode() {
        return yourZkDataNode;
    }

    public void setYourZkDataNode(String yourZkDataNode) {
        this.yourZkDataNode = yourZkDataNode;
    }

}
