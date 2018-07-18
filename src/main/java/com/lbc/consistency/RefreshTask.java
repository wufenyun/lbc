/**
 * Package: com.lbc.schedule
 * Description: 
 */
package com.lbc.consistency;

import com.lbc.context.CacheContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 刷新缓存任务
 *
 * @author wufenyun
 */
public class RefreshTask<K,V> implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTask.class);
    
    private CacheContext context;
    private K key;

    public RefreshTask(CacheContext context, K key) {
        this.context = context;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            context.getGlobalSingleCache().refresh(key);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

}
