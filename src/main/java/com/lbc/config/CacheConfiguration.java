/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lbc.cacheloader.CacheLoader;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:49:13
 * @author wufenyun 
 */
public class CacheConfiguration {
    
    private Map<Object,CacheLoader<Object,Object>> registedMap = new ConcurrentHashMap<>();
    
    public void regist(Object key,CacheLoader<Object,Object> loader) {
        registedMap.put(key, loader);
    }
    
    public Map<Object,CacheLoader<Object,Object>> getRegistedMap() {
        return registedMap;
    }
}
