/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lbc.exchanger.CacheExchanger;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:49:13
 * @author wufenyun 
 */
public class CacheConfiguration {
    
    /**
     * 定时刷新缓存任务初次执行延迟时间，默认立即执行
     */
    private long initialDelay = 0;
    /**
     * 定时刷新缓存任务执行间隔时间，默认1000毫秒
     */
    private long intervalMills = 1000;
    /**
     * 定时刷新缓存任务线程数，默认单线程
     */
    private int refreshThreads = 1;
    
   /* private Map<Object,CacheExchanger<Object,Object>> registedMap = new ConcurrentHashMap<>();
    
    public void regist(Object key,CacheExchanger<Object,Object> loader) {
        registedMap.put(key, loader);
    }
    
    public Map<Object,CacheExchanger<Object,Object>> getRegistedMap() {
        return registedMap;
    }*/

    public long getIntervalMills() {
        return intervalMills;
    }

    public void setIntervalMills(long intervalMills) {
        this.intervalMills = intervalMills;
    }

    public int getRefreshThreads() {
        return refreshThreads;
    }

    public void setRefreshThreads(int refreshThreads) {
        this.refreshThreads = refreshThreads;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }
}
