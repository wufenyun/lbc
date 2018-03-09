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
public interface Cache {

    <K, V> QueryingCollection<K, V> get(K key,Class<? extends CacheExchanger> clazz);

    <K, V> QueryingCollection<K, V> get(K key);
    
    <K, V> void put(K key,Collection<V> value);
    
    <K> void refresh(K key);
    
    <K, V> Map<K, CacheExchanger<K, V>> getInitialedCache();

    <K,V> void replace(K key, Collection<V> data);
}
