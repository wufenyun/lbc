package com.lbc.config;

/**
 * @description: 防缓存穿透配置
 * @author: wufenyun
 * @date: 2018-07-12 20
 **/
/**
 * 防缓存穿透相关配置
 */
public final class PreventPenetrationConfig {

    public final static int DEFAULT_CLEAR_BLOOM_INTERVAL_SECONDS = 60;

    private PreventPenetrationPolicy policy;
    /**
     * 清除布隆过滤器信息的时间间隔
     */
    private int clearIntervalSeconds;
    /**
     * 预期数量
     */
    private int expectedInsertions;

    public PreventPenetrationConfig(int expectedInsertions) {
        this(DEFAULT_CLEAR_BLOOM_INTERVAL_SECONDS,expectedInsertions);
    }

    public PreventPenetrationConfig(int clearIntervalSeconds, int expectedInsertions) {
        this.policy = PreventPenetrationPolicy.BLOOM_FILTER;
        this.clearIntervalSeconds = clearIntervalSeconds;
        this.expectedInsertions = expectedInsertions;
    }

    public PreventPenetrationPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(PreventPenetrationPolicy policy) {
        this.policy = policy;
    }

    public int getClearIntervalSeconds() {
        return clearIntervalSeconds;
    }

    public void setClearIntervalSeconds(int clearIntervalSeconds) {
        this.clearIntervalSeconds = clearIntervalSeconds;
    }

    public int getExpectedInsertions() {
        return expectedInsertions;
    }

    public void setExpectedInsertions(int expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    /**
     * 防止缓存穿透策略
     */
    public static enum PreventPenetrationPolicy {
        /**
         * 不需要防止缓存穿透
         */
        NO,
        /**
         * 布隆过滤器实现，可以防止缓存穿透，但也可能导致不能获取到最新的数据
         */
        BLOOM_FILTER;
    }
}

