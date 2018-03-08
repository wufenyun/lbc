/**
 * Package: com.lbc.schedule
 * Description: 
 */
package com.lbc.schedule;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.Cache;
import com.lbc.exchanger.CacheExchanger;

/**
 * Description:  
 * Date: 2018年3月7日 下午2:24:50
 * @author wufenyun 
 */
public class RefreshTask implements Runnable,TimingRefresh {
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTask.class);
    
    private Cache cache;
    
    public RefreshTask(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        refresh();
    }

    @Override
    public void refresh() {
        logger.info("start to reload data ");
        Map<Object, CacheExchanger<Object, Object>> map = cache.getInitialedCache();
        map.forEach((key,loader)->{
            try {
                if(loader.needRefresh(key)) {
                    logger.info("start to reload data " + key);
                    Collection<Object> c = loader.load(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
