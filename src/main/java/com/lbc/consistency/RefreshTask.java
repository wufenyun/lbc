/**
 * Package: com.lbc.schedule
 * Description: 
 */
package com.lbc.consistency;

import java.util.List;

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
        List<V> data;
        try {
            logger.info("start to refresh cache data，key:{}",key);
            data = exchanger.load(key);
            cache.replace(key,data);
            logger.info("finished refresh the {}'s data,record count {}",key,data.size());
            logger.debug("finished refresh the {}'s data,data: {}",key,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
