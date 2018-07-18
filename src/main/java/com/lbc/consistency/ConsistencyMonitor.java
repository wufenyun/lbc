/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.consistency;

/**
 * 缓存数据一致性状态监控器接口
 *
 * Date: 2018年3月2日 下午6:09:00
 * @author wufenyun 
 */
public interface ConsistencyMonitor extends Monitor {

    /** 
     * 通知刷新缓存任务刷新缓存,支持批量
     * @param keys
     */
    void notifyRefresh(Object[] keys);
    
    /** 
     * 通知刷新缓存任务刷新缓存
     * @param key
     */
    void notifyRefresh(Object key);
    
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
