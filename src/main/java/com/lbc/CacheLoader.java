/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.Collection;

/**
 * Description:  
 * Date: 2018年3月2日 下午4:06:17
 * @author wufenyun 
 */
public interface CacheLoader<K,V> extends Status {
    
    Collection<V> load(K key);
}
