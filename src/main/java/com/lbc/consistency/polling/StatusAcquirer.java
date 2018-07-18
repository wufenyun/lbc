/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.consistency.polling;

/**
 * 缓存状态获取器
 * 
 * @author wufenyun
 */
public interface StatusAcquirer {
    
    /** 
     * 是否待更新状态    
     * @param key 键
     * @return
     */
    <K> boolean needRefresh(K key);
}
