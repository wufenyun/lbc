/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.consistency.event.support;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:50:37
 * @author wufenyun 
 */
public interface CacheStatusChanger {

    void updateStatus(Object... keys);
}
