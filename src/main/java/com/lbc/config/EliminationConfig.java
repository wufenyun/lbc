package com.lbc.config;

/**
 * @description: 缓存淘汰机制配置
 * @author: wufenyun
 * @date: 2018-07-12 19
 **/
/**
 * 缓存淘汰相关配置
 */
public final class EliminationConfig {

    /**
     * 缓存最大阈值，按key的多少计算，默认-1(即不限制缓存数量)，用户可以自己设定
     */
    private int cacheSizeThreshold;
    /**
     * 缓存淘汰策略
     */
    private EliminationPolicy eliminationPolicy;

    public EliminationConfig(int cacheSizeThreshold, EliminationPolicy eliminationPolicy) {
        this.cacheSizeThreshold = cacheSizeThreshold;
        this.eliminationPolicy = eliminationPolicy;
    }

    public int getCacheSizeThreshold() {
        return cacheSizeThreshold;
    }

    public void setCacheSizeThreshold(int cacheSizeThreshold) {
        this.cacheSizeThreshold = cacheSizeThreshold;
    }

    public EliminationPolicy getEliminationPolicy() {
        return eliminationPolicy;
    }

    public void setEliminationPolicy(EliminationPolicy eliminationPolicy) {
        this.eliminationPolicy = eliminationPolicy;
    }

    public static enum EliminationPolicy {
        /**
         * 不需要缓存淘汰机制
         */
        NO,
        /**
         * LRU策略
         */
        LRU,
        /**
         * 最近不经常使用
         */
        LFU;
    }
}