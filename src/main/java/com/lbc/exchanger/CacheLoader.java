/**
 * Package: com.lbc.cacheloader
 * Description: 
 */
package com.lbc.exchanger;

import java.util.Collection;

/**
 * Description:  
 * Date: 2018年3月7日 下午3:15:05
 * @author wufenyun 
 */
public interface CacheLoader<K,V> {
    
    Collection<V> load(K key) throws Exception;
}
