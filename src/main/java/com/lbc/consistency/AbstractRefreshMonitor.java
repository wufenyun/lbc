/**
 * Package: com.lbc.refresh
 * Description: 
 */
package com.lbc.consistency;

import com.lbc.Cache;
import com.lbc.config.MonitorConfig;
import com.lbc.context.CacheContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 实现了ConsistencyMonitor部分方法。使用了模板方法模式，对两种监控模式通用方法进行了抽取。
 * 使用线程池执行缓存刷新任务，线程数可由使用者配置。
 *
 * @author wufenyun
 */
public abstract class AbstractRefreshMonitor implements ConsistencyMonitor {
    
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
    public void stopMonitor() {
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

    protected MonitorConfig getMonitorConfig() {
        return context.getConfiguration().getMonitorConfig();
    }

    class RefreshExecutor implements Refresher {

        private ThreadPoolExecutor executor;
        
        public RefreshExecutor() {
            executor = new ThreadPoolExecutor(getMonitorConfig().getRefreshThreads(),
                    getMonitorConfig().getRefreshThreads(), 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(50),new ThreadFactory() {
                        
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
            
            Cache cache = context.getGlobalSingleCache();
            if(!cache.getAllKeyMap().containsKey(key)) {
                logger.warn("The key-{} not exist,and will not to refresh this key's data",key);
                return;
            }
            
            executor.submit(new RefreshTask(context, key));
        }

        @Override
        public void refreshAll() {
            //暂未实现
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
