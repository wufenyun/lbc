/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.context;

import com.lbc.Cache;
import com.lbc.config.LbcConfiguration;
import com.lbc.context.event.EventMulticaster;
import com.lbc.CacheLoader;

/**
 * 本地批量缓存上下文环境，主要负责管理@see Cache的生命周期：
 *  初始化，包括创建缓存对象、处理配置信息、事件监听器等；
 *  执行缓存预加载；
 *  缓存创建以及初始化工作完成后相关事件，比如开启缓存一致性监控等等；
 *  缓存销毁；
 *
 * Date: 2018年3月5日 上午10:44:59
 * @author wufenyun 
 */
public interface CacheContext {

    /**
     * 设置配置文件
     *
     * @param lbcConfiguration 缓存配置信息
     */
    void setConfiguration(LbcConfiguration lbcConfiguration);

    /**
     * 初始化cache对象，包括设置参数等操作
     */
    void init();

    /**
     * 缓存预加载，根据使用者自己实现的CacheLoader接口加载数据；
     * CacheExchanger具备加载方法和预加载方法，具体@see CacheLoader接口
     *
     * @param cacheLoader 缓存数据加载器
     * @param <K>
     * @param <V>
     */
    <K, V> void preLoading(CacheLoader<K, V> cacheLoader);

    /**
     * 缓存对象创建完成时需要执行的操作，比如开启监控等等
     *
     */
    void onCacheBuilt();

    /**
     * 关闭本地缓存框架
     */
    void close();

    /** 
     * 获取应用中全局唯一的缓存对象
     * @return 应用中全局唯一的缓存对象
     */
    Cache getGlobalSingleCache();
    
    /** 
     * 获取配置管理对象
     * @return 缓存配置信息
     */
    LbcConfiguration getConfiguration();

    /**
     * 获取事件消息多播器
     * @return 事件消息多播器
     */
    EventMulticaster getEventMulticaster();
}
