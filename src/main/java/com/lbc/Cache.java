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
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public interface Cache<K,V> {

    QueryingCollection<K, V> get(K k,CacheExchanger<K, V> loader);
    
    void put(K k,Collection<V> value);
    
    void refresh(K k);
    
    Map<K, CacheExchanger<K, V>> getInitialedCache();
}
