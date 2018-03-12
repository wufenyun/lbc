/**
 * Package: com.lbc.cacheloader
 * Description: 
 */
package com.lbc.exchanger;

import java.util.Collection;

/**
 * 缓存加载器，负责加载数据到内存
 * Date: 2018年3月7日 下午3:15:05
 * @author wufenyun 
 */
public interface CacheLoader<K,V> {
    
    /** 
     * 根据key加载数据到内存
     * @param key 键
     * @return
     * @throws Exception 加载可能会抛异常
     */
    Collection<V> load(K key) throws Exception;
    
}
