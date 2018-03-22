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
import com.lbc.refresh.AbstractRefreshMonitor;
import com.lbc.refresh.StatusAcquirer;

/**
 * Description:  
 * Date: 2018年3月9日 上午11:08:03
 * @author wufenyun 
 */
public class PolllingRefreshMonitor extends AbstractRefreshMonitor implements Polling {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private StatusAcquirer statusAcquirer;
    
    public PolllingRefreshMonitor(CacheContext context) {
        super(context);
    }
    
    @Override
    public void doMonitor() {
        if(null == statusAcquirer) {
            logger.warn("用户未创建缓存状态获取器(StatusAcquirer)，缓存将不会刷新，请注意！！！！！！！！！");
            return;
        }
        
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            
            @Override
            public void run() {
                while(true) {
                    Map<?, ?> keyLoaders = context.getGloableSingleCache().getAllKeyMap();
                    keyLoaders.forEach((k,v)->{
                        logger.debug("判断key:{}是否需要刷新",k);
                        if(statusAcquirer.needRefresh(k)) {
                            notifyRefresh(k);
                        }
                    });
                    try {
                        Thread.sleep(context.getConfiguration().getIntervalMills());
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
