/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public interface Cache<K,V> {

    V get(K k,CacheLoader<K, V> loader);
    
    void put(K k,V value);
    
    void refresh(K k);
}
