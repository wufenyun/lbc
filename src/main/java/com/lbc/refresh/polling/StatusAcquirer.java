/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.refresh.polling;

/**
 * 缓存状态获取器
 * 
 * Date: 2018年3月2日 下午6:03:48
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
