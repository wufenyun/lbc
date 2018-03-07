/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.exchanger;

/**
 * Description:  
 * Date: 2018年3月2日 下午6:03:48
 * @author wufenyun 
 * @param <K>
 */
public interface Status<K> {
    
    /** 
     * 是否待更新状态  
     * @return
     */
    boolean needRefresh(K key);
}
