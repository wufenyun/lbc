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
public class LbcConfiguration {

    private MonitorConfig monitorConfig;
    private PreventPenetrationConfig preventPenetrationConfig;
    private EliminationConfig eliminationConfig;

    private LbcConfiguration(Builder builder) {
        this.monitorConfig = builder.monitorConfig;
        this.eliminationConfig = builder.eliminationConfig;
        this.preventPenetrationConfig = builder.preventPenetrationConfig;
    }

    public MonitorConfig getMonitorConfig() {
        return monitorConfig;
    }

    public PreventPenetrationConfig getPreventPenetrationConfig() {
        return preventPenetrationConfig;
    }

    public EliminationConfig getEliminationConfig() {
        return eliminationConfig;
    }

    public static class Builder {

        private MonitorConfig monitorConfig;
        private PreventPenetrationConfig preventPenetrationConfig;
        private EliminationConfig eliminationConfig;

        public Builder zkMonitorConfig(String zkConnection) {
            /**
             * 应用专属zk数据节点名，用以EVNET_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
             */
            return zkMonitorConfig(MonitorConfig.DEFAULT_REFRESH_THREADS,zkConnection,MonitorConfig.DEFAULT_ZKDATA_NODENAME);
        }

        public Builder zkMonitorConfig(int refreshThreads,String zkConnection) {
            return zkMonitorConfig(refreshThreads,zkConnection,MonitorConfig.DEFAULT_ZKDATA_NODENAME);
        }

        public Builder zkMonitorConfig(String zkConnection,String yourZkDataNode) {
            return zkMonitorConfig(MonitorConfig.DEFAULT_REFRESH_THREADS,zkConnection,yourZkDataNode);
        }

        public Builder zkMonitorConfig(int refreshThreads,String zkConnection,String yourZkDataNode) {
            if(refreshThreads < 1) {
                refreshThreads = MonitorConfig.DEFAULT_REFRESH_THREADS;
            }

            if(yourZkDataNode == null || yourZkDataNode.isEmpty()) {
                yourZkDataNode = MonitorConfig.DEFAULT_ZKDATA_NODENAME;
            }

            this.monitorConfig = new MonitorConfig(refreshThreads,zkConnection,yourZkDataNode);
            return this;
        }

        public Builder pollingMonitorConfig(int refreshThreads,long initialDelay,long intervalMills) {
            if(refreshThreads < 1) {
                refreshThreads = MonitorConfig.DEFAULT_REFRESH_THREADS;
            }

            if(initialDelay < 0) {
                initialDelay = MonitorConfig.DEFAULT_INITIAL_DELAY;
            }

            if (intervalMills < 1) {
                intervalMills = MonitorConfig.DEFAULT_INTERVAL_MILLS;
            }
            this.monitorConfig = new MonitorConfig(refreshThreads,initialDelay,intervalMills);
            return this;
        }

        public Builder bloomPreventPenetrationConfig(int expectedInsertions) {
            this.preventPenetrationConfig = new PreventPenetrationConfig(expectedInsertions);
            return this;
        }

        public Builder bloomPreventPenetrationConfig(int clearIntervalSeconds, int expectedInsertions) {
            if(clearIntervalSeconds < 0) {
                clearIntervalSeconds = PreventPenetrationConfig.DEFAULT_CLEAR_BLOOM_INTERVAL_SECONDS;
            }

            if (expectedInsertions < 0) {
                expectedInsertions = PreventPenetrationConfig.DEFAULT_EXPECTED_INSERTIONS;
            }
            this.preventPenetrationConfig = new PreventPenetrationConfig(clearIntervalSeconds,expectedInsertions);
            return this;
        }

        public Builder lruEliminationConfig(int cacheSizeThreshold) {
            return eliminationConfig(cacheSizeThreshold, EliminationConfig.EliminationPolicy.LRU);
        }

        public Builder eliminationConfig(int cacheSizeThreshold, EliminationConfig.EliminationPolicy eliminationPolicy) {
            if (cacheSizeThreshold < 0) {
                throw new IllegalArgumentException("if open elimination,the cacheSizeThreshold should be seted!");
            }
            this.eliminationConfig = new EliminationConfig(cacheSizeThreshold,eliminationPolicy);
            return this;
        }

        public LbcConfiguration build() {
            return new LbcConfiguration(this);
        }
    }

}
