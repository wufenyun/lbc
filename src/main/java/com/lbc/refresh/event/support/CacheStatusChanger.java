/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.refresh.event.support;

/**
 * Description:  
 * Date: 2018年3月12日 下午5:50:37
 * @author wufenyun 
 */
public interface CacheStatusChanger {

    void updateStatus(String key,Object data);
}
