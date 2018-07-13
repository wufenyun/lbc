package com.lbc.config;

/**
 * @description: 缓存一致性监控配置
 * @author: wufenyun
 * @date: 2018-07-12 19
 **/
public final class MonitorConfig {

    public final static int DEFAULT_INITIAL_DELAY = 0;
    public final static int DEFAULT_INTERVAL_MILLS = 1000;
    public final static int DEFAULT_REFRESH_THREADS = 1;
    public final static String ROOTPATH = "/localBatchCache";
    public final static String DEFAULT_ZKDATA_NODENAME = "/defaultRefresherDataNode";

    /**
     * 缓存数据一致性监控模式
     */
    private MonitorModel monitorModel;
    /**
     * 执行刷新缓存任务线程数,默认为1
     */
    private int refreshThreads;

    /**
     * zookeeper地址
     */
    private String zkConnection;
    /**
     * 应用专属zk数据节点名，用以EVENT_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
     */
    private String yourZkDataNode;

    /**
     * 定时刷新缓存任务初次执行延迟时间
     */
    private long initialDelay;
    /**
     * 定时刷新缓存任务执行间隔时间
     */
    private long intervalMills;

    public MonitorConfig(MonitorModel monitorModel, String zkConnection) {
        this(DEFAULT_REFRESH_THREADS,zkConnection,DEFAULT_ZKDATA_NODENAME);
    }

    public MonitorConfig(int refreshThreads,String zkConnection) {
        /**
         * 应用专属zk数据节点名，用以EVNET_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
         */
        this(refreshThreads,zkConnection,DEFAULT_ZKDATA_NODENAME);
    }

    public MonitorConfig(int refreshThreads,String zkConnection,String yourZkDataNode) {
        this.monitorModel = MonitorModel.EVENT_ZK;
        this.refreshThreads = refreshThreads;
        this.zkConnection = zkConnection;
        this.yourZkDataNode = yourZkDataNode;
    }

    public MonitorConfig(int refreshThreads,long initialDelay,long intervalMills) {
        this.monitorModel = MonitorModel.POLLING;
        this.refreshThreads = refreshThreads;
        this.initialDelay = initialDelay;
        this.intervalMills = intervalMills;
    }

    public MonitorModel getMonitorModel() {
        return monitorModel;
    }

    public void setMonitorModel(MonitorModel monitorModel) {
        this.monitorModel = monitorModel;
    }

    public int getRefreshThreads() {
        return refreshThreads;
    }

    public void setRefreshThreads(int refreshThreads) {
        this.refreshThreads = refreshThreads;
    }

    public String getZkConnection() {
        return zkConnection;
    }

    public void setZkConnection(String zkConnection) {
        this.zkConnection = zkConnection;
    }

    public String getYourZkDataNode() {
        return yourZkDataNode;
    }

    public void setYourZkDataNode(String yourZkDataNode) {
        this.yourZkDataNode = yourZkDataNode;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public long getIntervalMills() {
        return intervalMills;
    }

    public void setIntervalMills(long intervalMills) {
        this.intervalMills = intervalMills;
    }

    /**
     * 缓存数据一致性监控模式,目前支持轮询模式和zk实现的事件驱动模式
     */
    public static enum MonitorModel {
        /**
         * 不需要监控缓存一致性的情况
         */
        NO,
        /**
         * 采用轮询的模式来监控缓存是否需要刷新
         */
        POLLING,
        /**
         * 借助zookeeper实现事件驱动模式来监控缓存是否需要刷新
         */
        EVENT_ZK;
    }
}
