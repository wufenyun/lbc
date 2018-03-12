/**
 * Package: com.lbc.schedule
 * Description: 
 */
package com.lbc.refresh;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.Cache;
import com.lbc.exchanger.CacheExchanger;

/**
 * Description:  
 * Date: 2018年3月7日 下午2:24:50
 * @author wufenyun 
 */
public class RefreshTask<K,V> implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTask.class);
    
    private Cache cache;
    private K key;
    private CacheExchanger<K,V> exchanger;
    
    public RefreshTask(Cache cache,K key,CacheExchanger<K,V> exchanger) {
        this.cache = cache;
        this.key = key;
        this.exchanger = exchanger;
    }


    @Override
    public void run() {
        Collection<V> data;
        try {
            data = exchanger.load(key);
            logger.info("data:{}",data);
            cache.replace(key,data);        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
