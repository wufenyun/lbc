/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.Collection;

import com.lbc.cacheloader.CacheLoader;
import com.lbc.wrap.Wrapper;

/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public interface Cache<K,V> {

	Wrapper<V> get(K k,CacheLoader<K, V> loader);
    
    void put(K k,Collection<V> value);
    
    void refresh(K k);
}
