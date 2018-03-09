/**
 * Package: com.lbc.schedule
 * Description: 
 */
package com.lbc.refresh;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.Cache;
import com.lbc.exchanger.CacheExchanger;

/**
 * Description:  
 * Date: 2018年3月7日 下午2:24:50
 * @author wufenyun 
 */
public class RefreshTask<V> implements Callable<V> {
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTask.class);
    
    private Cache cache;
    private Object key;
    private CacheExchanger exchanger;
    
    public RefreshTask(Cache cache,Object key,CacheExchanger exchanger) {
        this.cache = cache;
        this.key = key;
        this.exchanger = exchanger;
    }

    @Override
    public V call() throws Exception {
        
        Collection<?> data = exchanger.load(key);
        cache.replace(key,data);
        return (V) data;
    }

}
