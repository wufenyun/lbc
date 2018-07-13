/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.consistency.eventdriven.support;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:50:37
 * @author wufenyun 
 */
public interface CacheStatusChanger {

    void updateStatus(Object... keys);
}
