/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

/**
 * Description:  
 * Date: 2018年3月2日 下午4:06:17
 * @author wufenyun 
 */
public interface CacheLoader<K,V> extends Status {
    
    V load(K key);
}
