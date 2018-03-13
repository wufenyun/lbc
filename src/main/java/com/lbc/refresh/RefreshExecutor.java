/**
 * Package: com.lbc.refresh
 * Description: 
 */
package com.lbc.refresh;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lbc.Cache;
import com.lbc.CacheContext;
import com.lbc.config.CacheConfiguration;
import com.lbc.exchanger.CacheExchanger;

/**
 * Description:  
 * Date: 2018年3月9日 下午2:50:12
 * @author wufenyun 
 */
public class RefreshExecutor implements Refresher {

    private ThreadPoolExecutor executor;
    private CacheContext context;
    
    public RefreshExecutor(CacheContext context) {
        this.context = context;
        CacheConfiguration configuration = context.getConfiguration();
        executor = new ThreadPoolExecutor(configuration.getRefreshThreads(),
                configuration.getRefreshThreads(), 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100),new ThreadFactory() {
                    
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r,"RefreshTask-TD");
                    }
                });
    }
    
    @Override
    public <K> void refresh(K key) {
        if(null == key) {
            return;
        }
        Cache cache = context.getGloableSingleCache();
        CacheExchanger<?, ?> exchanger = cache.getAllKeyMap().get(key);
        executor.submit(new RefreshTask(cache, key, exchanger));
    }

    @Override
    public void refreshAll() {
        
    }

    @Override
    public <K> void refresh(K[] keys) {
        if(null == keys || keys.length == 0) {
            return;
        }
        
        for(int i=0;i<keys.length;i++) {
            refresh(keys[i]);
        }
    }

}
