/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc;

import com.lbc.config.CacheConfiguration;

/**
 * 本地批量缓存上下文环境，负责维护缓存的配置信息、缓存预加载、缓存一致性监控等等
 *   
 * Date: 2018年3月5日 上午10:44:59
 * @author wufenyun 
 */
public interface CacheContext {
    
    /** 
     * 获取应用中全局唯一的缓存对象
     * @return
     */
    Cache getGloableSingleCache();
    
    /** 
     * 获取配置管理对象
     * @return
     */
    CacheConfiguration getConfiguration();
    
}
