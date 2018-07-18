/**
 * Package: com.lbc.refresh.event.support
 * Description: 
 */
package com.lbc.consistency.eventdriven.support;

/**
 * 通知缓存刷新接口
 *
 * @author wufenyun
 */
public interface CacheStatusChanger {

    void updateStatus(Object... keys);
}
