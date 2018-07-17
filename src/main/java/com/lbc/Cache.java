/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.List;
import java.util.Map;

import com.lbc.wrap.QueryingCollection;

/**
 * lbc(local batch cache),依赖于spring框架实现，提供：缓存预加载，本地缓存查询，缓存数据一致性解决方案，缓存淘汰机制等等功能
 *
 * 应用场景：适用于字典型或者配置型数据、需要缓存预加载、需要保证缓存数据与原始数据一致性、需要对本地缓存数据进行查询等等场景；
 *
 * 1.1 缓存预加载
 * lbc支持应用启动完成之前加载用户的热点数据，可以防止应用启动完成初始阶段缓存击穿带来的响应慢甚至雪崩问题
 *
 * 1.2 支持本地缓存数据一致性
 * 很多项目都会涉及到字典型或者配置型数据，这种类型的数据虽然变化频率不是特别高，但是也会存在变动的情况，所以缓存数据一致性的需求是必须支持的。
 * lbc支持观察者和轮询两种模式，观察者模式目前只实现了依赖zookeeper的实现，轮询模式需要用户提供获取是否需要更新状态的接口
 *
 * 1.3 本地内存查询
 *  lbc封装了查询功能，支持两种查询方式，能够满足一般的查询需求，后续会扩展更多的查询功能
 *
 * 1.4 缓存淘汰机制
 * lbc目前提供了lru的缓存淘汰机制，其中用户指定预加载的热点数据不会参与到淘汰的数据中，因为这部分数据使用频率很高，没必要加入淘汰机制
 *
 *其他
 *
 * 事件机制：事件机制主要用于支持lbc的可扩展性。用户可以实现自定义的监听器监听感兴趣的事件，比如缓存刷新事件，用户可以跟踪监控到缓存的相关状态信息
 * 防缓存击穿策略：缓存击穿在lbc场景下不会频繁发生，一个是lb主要针对的是字典型数据，第二个缓存预加载已经加载了热点数据。目前采用的互斥锁方式防止缓存击穿带来的雪崩问题。
 * 防缓存穿透策略：为了防止缓存穿透带来的问题，lbc提供布隆过滤的策略
 * 本地缓存状态信息维护，计划使用jmx
 *
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public interface Cache {

    /**
     * 向缓存中存入数据
     * @param key 键
     * @param data 值
     * @param cacheLoader 缓存加载器
     */
    <K, V> void put(K key, List<V> data, CacheLoader<K, V> cacheLoader);

    /**
     * 刷新缓存
     * @param key 键值
     * @return 旧值
     */
    <K, V> QueryingCollection<V> refresh(K key);

    /**
     * 根据key替换其值
     * @param key
     * @param data
     */
    <K,V> void replace(K key, List<V> data);

    /**
     * 根据key获取缓存集合，返回的集合对象提供查询功能；
     * 如果获取到的数据为空则会返回空的QueryingCollection对象;
     * 注意：如果缓存中不存在此key的数据，则调用相应的CacheLoader加载到缓存中然后返回
     * @param key 键值
     * @param clazz 缓存加载器Class类型
     * @return 可查询的缓存集合
     */
    <K, V> QueryingCollection<V> get(K key,Class<? extends CacheLoader<K, V>> clazz);

    /**
     * 根据key获取缓存集合，返回的集合对象提供查询功能；
     * 如果获取到的数据为空则会返回空的QueryingCollection对象;
     * 注意：如果缓存中不存在此key的数据，则返回空
     * @param key 键值
     * @return 可查询的缓存集合
     */
    <K, V> QueryingCollection<V> get(K key);

    /**
     * 判断本地缓存中是否包含此key
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> boolean contains(K key);

    /** 
     * 获取应用启动时已经初始化了缓存键值对
     * @return key和相应缓存加载器对象
     */
    <K, V> Map<K, CacheLoader<K, V>> getInitialedKeyMap();
    
    /** 
     * 获取当前缓存中所有的key和缓存加载器键值对
     * @return
     */
    <K, V> Map<K, CacheLoader<K, V>> getAllKeyMap();

}
