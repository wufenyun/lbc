/**
 * Package: com.lbc.refresh.polling
 * Description: 
 */
package com.lbc.refresh.polling;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.CacheContext;
import com.lbc.refresh.RefreshExecutor;
import com.lbc.refresh.Refresher;
import com.lbc.refresh.StatusAcquirer;
import com.lbc.refresh.StatusMonitor;

/**
 * Description:  
 * Date: 2018年3月9日 上午11:08:03
 * @author wufenyun 
 */
public class PolllingRefreshMonitor implements StatusMonitor,PollingMonitor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private Refresher refresher;
    private StatusAcquirer statusAcquirer;
    private CacheContext cacheContext;
    
    public PolllingRefreshMonitor(CacheContext cacheContext) {
        this.cacheContext = cacheContext;
        refresher = new RefreshExecutor(cacheContext);
    }
    
    
    @Override
    public void startMonitoring() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            
            @Override
            public void run() {
                while(true) {
                    Map<?, ?> keyLoaders = cacheContext.getGloableSingleCache().getAllKeyMap();
                    keyLoaders.forEach((k,v)->{
                        logger.debug("判断key:{}是否需要刷新",k);
                        if(statusAcquirer.needRefresh(k)) {
                            logger.info("开始刷新缓存，key:{}",k);
                            refresher.refresh(k);
                        }
                    });
                    try {
                        Thread.sleep(cacheContext.getConfiguration().getIntervalMills());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void setStatusAcquirer(StatusAcquirer sAcquirer) {
        this.statusAcquirer = sAcquirer;
    }

}
