/**
 * Package: com.lbc.config
 * Description: 
 */
package com.lbc;

import java.util.Map;

import com.lbc.config.CacheConfiguration;

/**
 * Description:  
 * Date: 2018年3月5日 上午10:44:59
 * @author wufenyun 
 */
public interface CacheContext {
    
    Cache openSingletonCache();
    
    CacheConfiguration getConfiguration();
    
    Map getKeyLoaders();
    
    Map getInitianizedKeys();
}
