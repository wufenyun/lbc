/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc.exchanger;

import java.util.List;

/**
 * 缓存交换器，除了具备缓存加载器的加载数据到内存的功能，
 * 还定义了需要在应用启动完成之前进行缓存预加载的加载数据接口和指定预加载缓存key
 * 
 * Date: 2018年3月2日 下午4:06:17
 * @author wufenyun 
 */
public interface CacheExchanger<K,V> extends CacheLoader<K,V> {
    
    /** 
     * 预加载key
     * @return
     */
    K prelaodingKey();
    
    /** 
     * 预加载数据到内存
     * @return
     * @throws Exception
     */
    List<V> prelaoding() throws Exception;
    
}
