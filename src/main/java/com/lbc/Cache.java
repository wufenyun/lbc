/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.Collection;
import java.util.Map;

import com.lbc.exchanger.CacheExchanger;
import com.lbc.wrap.QueryingCollection;

/**
 * 本地批量缓存接口，提供：缓存预加载，本地缓存查询，缓存数据一致性解决方案
 * 
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public interface Cache {

    /** 
     * 根据key获取缓存集合，返回的集合对象提供查询功能；
     * 注意：如果缓存中不存在此key的数据，则调用相应的CacheExchanger加载到缓存中然后返回
     * @param key 键值
     * @param clazz 缓存交换器Class类型
     * @return 可查询的缓存集合
     */
    <K, V> QueryingCollection<K, V> get(K key,Class<? extends CacheExchanger<K, V>> clazz);

    /** 
     * 根据key获取缓存集合，返回的集合对象提供查询功能；
     * 注意：如果缓存中不存在此key的数据，则返回空
     * @param key 键值
     * @return 可查询的缓存集合
     */
    <K, V> QueryingCollection<K, V> get(K key);
    
    /** 
     * 刷新缓存
     * @param key 键值
     */
    <K> void refresh(K key);
    
    /** 
     * 获取应用启动时已经初始化了缓存键值对
     * @return key和相应缓存交换器对象
     */
    <K, V> Map<K, CacheExchanger<K, V>> getInitialedKeyMap();
    
    /** 
     * 获取当前缓存中所有的key和缓存交换器键值对  
     * @return
     */
    <K, V> Map<K, CacheExchanger<K, V>> getAllKeyMap();
    
    /** 
     * 根据key替换其值 
     * @param key
     * @param data
     */
    <K,V> void replace(K key, Collection<V> data);

    /** 
     * 向缓存中存入数据
     * @param key 键
     * @param data 值
     * @param exchanger 缓存交换器
     */
    <K, V> void put(K key, Collection<V> data, CacheExchanger<K, V> exchanger);

}
