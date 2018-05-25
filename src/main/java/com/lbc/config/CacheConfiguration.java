/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

/**
 * lbc配置类，负责管理lbc所有的配置信息
 * 
 * Date: 2018年3月5日 上午10:49:13
 * @author wufenyun 
 */
public class CacheConfiguration {
    
    /**
     * 定时刷新缓存任务初次执行延迟时间
     */
    private long initialDelay;
    /**
     * 定时刷新缓存任务执行间隔时间
     */
    private long intervalMills;
    /**
     * 定时刷新缓存任务线程数
     */
    private int refreshThreads;
    /**
     * 缓存最大阈值，按key的多少计算，默认-1(即不限制缓存数量)，用户可以自己设定
     */
    private int cacheSizeThreshold;
    /**
     * 缓存数据一致性监控模式
     */
    private MonitorModel monitorModel;
    /**
     * zookeeper地址
     */
    private String zkConnection;
    /**
     * 应用专属zk数据节点名，用以EVNET_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
     */
    private String yourZkDataNode;

    private CacheConfiguration() {
    }

    private CacheConfiguration(Builder builder) {
        this.cacheSizeThreshold = builder.cacheSizeThreshold;
        this.initialDelay = builder.initialDelay;
        this.intervalMills = builder.intervalMills;
        this.monitorModel = builder.monitorModel;
        this.refreshThreads = builder.refreshThreads;
        this.zkConnection = builder.zkConnection;
        this.yourZkDataNode = builder.yourZkDataNode;
    }

    public long getIntervalMills() {
        return intervalMills;
    }

    public int getRefreshThreads() {
        return refreshThreads;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public MonitorModel getMonitorModel() {
        return monitorModel;
    }

    public String getZkConnection() {
        return zkConnection;
    }

    public int getCacheSizeThreshold() {
        return cacheSizeThreshold;
    }

    public String getYourZkDataNode() {
        return yourZkDataNode;
    }


    public static class Builder {
        /**
         * 定时刷新缓存任务初次执行延迟时间，默认立即执行
         */
        private long initialDelay = CacheConfiguration.Constant.DEFAULT_INITIAL_DELAY;
        /**
         * 定时刷新缓存任务执行间隔时间，默认1000毫秒
         */
        private long intervalMills = CacheConfiguration.Constant.DEFAULT_INTERVAL_MILLS;
        /**
         * 定时刷新缓存任务线程数，默认单线程
         */
        private int refreshThreads = CacheConfiguration.Constant.DEFAULT_REFRESH_THREADS;
        /**
         * 缓存最大阈值，按key的多少计算，默认-1(即不限制缓存数量)，用户可以自己设定
         */
        private int cacheSizeThreshold = CacheConfiguration.Constant.NO_CACHESIZE_LIMIT;
        /**
         * 缓存数据一致性监控模式
         */
        private CacheConfiguration.MonitorModel monitorModel = CacheConfiguration.MonitorModel.POLLING;
        /**
         * zookeeper地址
         */
        private String zkConnection;
        /**
         * 应用专属zk数据节点名，用以EVNET_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
         */
        private String yourZkDataNode = CacheConfiguration.Constant.DEFAULT_ZKDATA_NODENAME;

        public Builder intervalMills(long intervalMills) {
            this.intervalMills = intervalMills;
            return this;
        }

        public Builder refreshThreads(int refreshThreads) {
            this.refreshThreads = refreshThreads;
            return this;
        }

        public Builder initialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        public Builder monitorModel(CacheConfiguration.MonitorModel monitorModel) {
            this.monitorModel = monitorModel;
            return this;
        }

        public Builder zkConnection(String zkConnection) {
            this.zkConnection = zkConnection;
            return this;
        }

        public Builder cacheSizeThreshold(int cacheSizeThreshold) {
            this.cacheSizeThreshold = cacheSizeThreshold;
            return this;
        }

        public Builder yourZkDataNode(String yourZkDataNode) {
            this.yourZkDataNode = yourZkDataNode;
            return this;
        }

        public CacheConfiguration build() {
            return new CacheConfiguration(this);
        }
    }

    /**
     * 缓存数据一致性监控模式,目前支持轮询模式和zk实现的事件驱动模式
     */
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
        public final static int NO_CACHESIZE_LIMIT = -1;
        public final static String ROOTPATH = "/localBatchCache";
        public final static String DEFAULT_ZKDATA_NODENAME = "/defaultRefresherDataNode";
    }

}
