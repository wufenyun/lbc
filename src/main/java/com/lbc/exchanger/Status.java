/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.exchanger;

/**
 * 描述缓存是否需要刷新的状态接口
 * 
 * Date: 2018年3月2日 下午6:03:48
 * @author wufenyun 
 * @param <K>
 */
public interface Status<K> {
    
    /** 
     * 是否待更新状态    
     * @param key 键
     * @return
     */
    boolean needRefresh(K key);
}
