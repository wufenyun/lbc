/**
 * Package: com.lbc.refresh.polling
 * Description: 
 */
package com.lbc.consistency.polling;

/**
 * 轮询模式监控器
 *
 * @author wufenyun
 */
public interface Polling {
    
    /** 
     * 设置缓存状态获取器
     * @param sAcquirer
     */
    void setStatusAcquirer(StatusAcquirer sAcquirer);
}
