/**
 * Package: com.lbc.refresh
 * Description: 
 */
package com.lbc.refresh;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lbc.config.CacheConfiguration;

/**
 * Description:  
 * Date: 2018年3月9日 下午2:50:12
 * @author wufenyun 
 */
public class RefreshExecutor implements Refresher {

    private CacheConfiguration configuration;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(configuration.getRefreshThreads(),
            configuration.getRefreshThreads(), 1000, TimeUnit.MILLISECONDS, null);
    
    @Override
    public <K> void refresh(K key) {
        executor.submit(new RefreshTask(null, key, null));
    }

    @Override
    public void refreshAll() {
        
    }

}
