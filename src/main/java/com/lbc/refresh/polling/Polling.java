/**
 * Package: com.lbc.refresh.polling
 * Description: 
 */
package com.lbc.refresh.polling;

/**
 * 轮询模式监控器
 * Date: 2018年3月9日 下午5:16:50
 * @author wufenyun 
 */
public interface Polling {
    
    /** 
     * 设置缓存状态获取器
     * @param sAcquirer
     */
    void setStatusAcquirer(StatusAcquirer sAcquirer);
}
