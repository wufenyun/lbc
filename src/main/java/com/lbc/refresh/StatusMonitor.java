/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.refresh;

/**
 * 缓存数据一致性状态监控器
 * Date: 2018年3月2日 下午6:09:00
 * @author wufenyun 
 */
public interface StatusMonitor {
    
    /** 
     *开启监控功能
     */
    void startMonitoring();
    
    void notifyRefresh(Object[] keys);
    
    void notifyRefresh(Object key);
    
    void close();
    
    /**
     * 缓存刷新器
     */
    interface Refresher {
        
        /** 
         * 根据key刷新缓存
         * @param key
         */
        <K> void refresh(K key);
        
        /** 
         * 根据多个key刷新缓存
         * @param keys
         */
        <K> void refresh(K[] keys);
        
        /** 
         * 刷新所有缓存
         */
        void refreshAll();
    }
}
