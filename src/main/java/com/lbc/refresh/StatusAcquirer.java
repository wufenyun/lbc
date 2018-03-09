/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.refresh;

/**
 * Description:  
 * Date: 2018年3月2日 下午6:03:48
 * @author wufenyun 
 */
public interface StatusAcquirer {
    
    /** 
     * 是否待更新状态  
     * @return
     */
    <K> boolean needRefresh(K key);
}
