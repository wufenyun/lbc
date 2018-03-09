/**
 * Package: com.lbc.refresh.polling
 * Description: 
 */
package com.lbc.refresh.polling;

import java.util.Map;

import com.lbc.Cache;
import com.lbc.config.CacheConfiguration;
import com.lbc.refresh.StatusMonitor;
import com.lbc.refresh.Refresher;
import com.lbc.refresh.StatusAcquirer;

/**
 * Description:  
 * Date: 2018年3月9日 上午11:08:03
 * @author wufenyun 
 */
public class PolllingRefreshMonitor implements StatusMonitor,PollingMonitor {

    private Map keyLoaders;
    private Refresher refresher;
    private StatusAcquirer statusAcquirer;
    private CacheConfiguration configuration;
    
    public PolllingRefreshMonitor(Cache cache,CacheConfiguration configuration) {
        
    }
    
    
    @Override
    public void startMonitoring() {
        while(true) {
            keyLoaders.forEach((k,v)->{
                if(statusAcquirer.needRefresh(k)) {
                    refresher.refresh(k);
                }
            });
            try {
                Thread.sleep(configuration.getIntervalMills());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void setStatusAcquirer(StatusAcquirer sAcquirer) {
        this.statusAcquirer = sAcquirer;
    }
    
}
