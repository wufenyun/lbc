/**
 * Package: com.lbc.refresh
 * Description: 
 */
package com.lbc.refresh;

import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.Cache;
import com.lbc.CacheContext;
import com.lbc.config.CacheConfiguration;
import com.lbc.exchanger.CacheExchanger;

/**
 * Description:  
 * Date: 2018年3月22日 下午2:42:21
 * @author wufenyun 
 */
public abstract class AbstractRefreshMonitor implements StatusMonitor {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected CacheContext context;
    private RefreshExecutor refreshExecutor;
    
    public AbstractRefreshMonitor(CacheContext context) {
        this.context = context;
        refreshExecutor = new RefreshExecutor();
    }
    
    
    @Override
    public void startMonitoring() {
        logger.info("The cache monitor begins to monitor the task");
        doMonitor();
    }
    
    @Override
    public void notifyRefresh(Object[] keys) {
        refreshExecutor.refresh(keys);
    }
    
    @Override
    public void notifyRefresh(Object key) {
        refreshExecutor.refresh(key);
    }
    
    @Override
    public void close() {
        doClose();
        refreshExecutor.closeRefresher();
    }
    
    /** 
     * 执行监控任务，具体实现由子类实现;
     * 如果有刷新缓存事件发生，通知刷新-notifyRefresh()
     */
    protected abstract void doMonitor();
    
    /** 
     * 关闭监控器，由具体子类实现
     */
    protected abstract void doClose();
    
    class RefreshExecutor implements Refresher {

        private ThreadPoolExecutor executor;
        
        public RefreshExecutor() {
            CacheConfiguration configuration = context.getConfiguration();
            executor = new ThreadPoolExecutor(configuration.getRefreshThreads(),
                    configuration.getRefreshThreads(), 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(50),new ThreadFactory() {
                        
                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r,"RefreshTask-TD");
                        }
                    });
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Override
        public <K> void refresh(K key) {
            if(null == key) {
                return;
            }
            
            Cache cache = context.getGloableSingleCache();
            if(!cache.getAllKeyMap().containsKey(key)) {
                logger.warn("The key-{} not exist,and will not to refresh this key's data",key);
                return;
            }
            
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
        
        public void closeRefresher() {
            if(null != executor) {
                executor.shutdown();
            }
        }
    }
}
