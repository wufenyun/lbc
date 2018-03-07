/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.exchanger;

import java.util.Collection;

/**
 * Description:  
 * Date: 2018年3月2日 下午4:06:17
 * @author wufenyun 
 */
public interface CacheExchanger<K,V> extends CacheLoader<K,V>,Status<K> {
    
    K initializeKey();
    
    Collection<V> initialize() throws Exception;

    boolean needRefresh(K key);
    
}
