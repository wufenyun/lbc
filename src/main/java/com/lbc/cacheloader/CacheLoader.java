/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.cacheloader;

import java.util.Collection;

import com.lbc.schedule.Status;

/**
 * Description:  
 * Date: 2018年3月2日 下午4:06:17
 * @author wufenyun 
 */
public interface CacheLoader<K,V> extends Status {
    
    Collection<V> initialize(K key) throws Exception;
    
    Collection<V> load(K key) throws Exception;
}
