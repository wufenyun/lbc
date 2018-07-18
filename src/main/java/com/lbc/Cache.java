/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.List;
import java.util.Map;

import com.lbc.wrap.QueryingCollection;

/**
 * Cache接口，lbc对使用者开放的核心接口，仅定义了面向使用者的获取、写入、刷新等常用功能，
 * 缓存的维护比如生命周期、监控、状态信息等由{@link com.lbc.context.CacheContext}实现。
 *
 * get(K key)方法只会从本地缓存获取数据，如果本地缓存中没有数据直接返回；
 * get(K key,Class<? extends CacheLoader<K, V>> clazz)方法需要使用者指定加载器类型，如果本地缓存中没有数据则使用CacheLoader加载从外部加载数据
 *
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
     * 获取当前缓存中所有的key和缓存加载器键值对
     * @return
     */
    <K, V> Map<K, CacheLoader<K, V>> getAllKeyMap();

}
