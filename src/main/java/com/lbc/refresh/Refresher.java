/**
 * Package: com.lbc.refresh
 * Description: 
 */
package com.lbc.refresh;

/**
 * 缓存刷新器
 * Date: 2018年3月9日 上午9:51:37
 * @author wufenyun 
 */
public interface Refresher {
    
    /** 
     * 根绝key刷新缓存
     * @param key
     */
    <K> void refresh(K key);
    
    /** 
     * 刷新所有缓存
     */
    void refreshAll();
}
