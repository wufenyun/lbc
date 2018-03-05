/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

import com.lbc.Cache;
import com.lbc.DefaultCache;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:49:57
 * @author wufenyun 
 */
public class DefaultCacheFactory implements CacheFactory {

    private Cache<Object, Object> cache = new DefaultCache<>();
    
    
    @Override
    public Cache<Object, Object> openSingletonCache() {
        return cache;
    }

    @Override
    public CacheConfiguration getConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

}
