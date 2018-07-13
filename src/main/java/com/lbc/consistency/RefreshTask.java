/**
 * Package: com.lbc.schedule
 * Description: 
 */
package com.lbc.consistency;

import com.lbc.CacheLoader;
import com.lbc.context.CacheContext;
import com.lbc.context.event.Event;
import com.lbc.context.event.EventFactory;
import com.lbc.wrap.QueryingCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Description:  
 * Date: 2018年3月7日 下午2:24:50
 * @author wufenyun 
 */
public class RefreshTask<K,V> implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTask.class);
    
    private CacheContext context;
    private K key;
    private CacheLoader<K,V> cacheLoader;
    
    public RefreshTask(CacheContext context, K key, CacheLoader<K,V> cacheLoader) {
        this.context = context;
        this.key = key;
        this.cacheLoader = cacheLoader;
    }

    @Override
    public void run() {
        try {
            logger.debug("start to refresh cache data,key:{}",key);
            if(!context.getGlobalSingleCache().contains(key)) {
                logger.info("lbc have not load key：{} data，don't refresh",key);
                return;
            }

            List<V> data = cacheLoader.load(key);
            context.getGlobalSingleCache().get(key);
            context.getGlobalSingleCache().replace(key,data);

            Event event = EventFactory.newRefreshedEvent(key,getExistSize(key),data.size());
            context.getEventMulticaster().multicast(event);
            logger.debug("finished refresh the {}'s data,data: {}",key,data);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    private long getExistSize(Object key) {
        QueryingCollection collection = context.getGlobalSingleCache().get(key);
        return (collection==null?0:collection.size());
    }
}
