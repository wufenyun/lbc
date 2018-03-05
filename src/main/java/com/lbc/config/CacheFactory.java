/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

import com.lbc.Cache;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:44:59
 * @author wufenyun 
 */
public interface CacheFactory {
    
    <K,V> Cache<K,V> openSingletonCache();
    
    CacheConfiguration getConfiguration();
}
