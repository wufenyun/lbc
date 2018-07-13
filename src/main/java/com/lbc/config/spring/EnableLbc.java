package com.lbc.config.spring;

import com.lbc.config.Configuration;
import com.lbc.config.EliminationConfig;
import com.lbc.config.MonitorConfig;
import com.lbc.config.PreventPenetrationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description:
 * @author: wufenyun
 * @date: 2018-07-11 17
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@org.springframework.context.annotation.Configuration
@Import(LbcRegistar.class)
public @interface EnableLbc {

    /*     ******************************  缓存数据一致性监控配置项  ******************************    */
    /**
     * 缓存数据一致性监控模式
     */
    MonitorConfig.MonitorModel monitorModel();

    /**
     * 定时刷新缓存任务线程数
     */
    int refreshThreads() default MonitorConfig.DEFAULT_REFRESH_THREADS;

    /**
     * zookeeper地址
     */
    String zkConnection() default "";

    /**
     * 应用专属zk数据节点名，用以EVNET_ZK监控模式下记录需要刷新的数据,建议用户自己定义来避免不同应用冲突
     */
    String yourZkDataNode() default MonitorConfig.DEFAULT_ZKDATA_NODENAME;

    /**
     * 定时刷新缓存任务初次执行延迟时间
     */
    long initialDelay() default MonitorConfig.DEFAULT_INITIAL_DELAY;

    /**
     * 定时刷新缓存任务执行间隔时间
     */
    long intervalMills() default MonitorConfig.DEFAULT_INTERVAL_MILLS;


    /*     ******************************  缓存淘汰策略配置项  ******************************    */
    /**
     * 缓存淘汰策略
     */
    EliminationConfig.EliminationPolicy eliminationPolicy() default EliminationConfig.EliminationPolicy.NO;

    /**
     * 缓存最大阈值，按key的数量计算，默认-1(即不限制缓存数量)，用户可以自己设定；
     * 如果大于0，则默认开启缓存淘汰机制
     */
    int cacheSizeThreshold() default -1;


    /*     ******************************  防止缓存穿透策略配置项  ******************************    */
    /**
     * 防止缓存穿透策略，默认无
     */
    PreventPenetrationConfig.PreventPenetrationPolicy preventPenetrationPolicy() default PreventPenetrationConfig.PreventPenetrationPolicy.NO;

    /**
     * 清除布隆过滤器信息的时间间隔
     */
    int clearIntervalSeconds() default -1;

    /**
     * 布隆过滤配置项，预期数据量，这里指key的数量
     */
    int expectedInsertions() default -1;
}