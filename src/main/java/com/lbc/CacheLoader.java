/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.List;

/**
 * 缓存加载器，负责加载数据到内存，除了具备加载数据到内存的功能，
 * 还定义了需要在应用启动完成之前进行缓存预加载的加载数据接口和指定预加载缓存key
 * 
 * Date: 2018年3月2日 下午4:06:17
 * @author wufenyun 
 */
public interface CacheLoader<K,V>{
    
    /** 
     * 预加载key,最佳实践：
     *  优先将K对象设置为String、整型等对象；
     *  自定义对象请重新实现equals()/hash()/toString()
     *
     * @return
     */
    K preLoadingKey();
    
    /** 
     * 预加载数据到内存
     * @return
     * @throws Exception
     */
    List<V> preLoading() throws Exception;

    /**
     * 根据key加载数据到内存
     * @param key 键
     * @return
     * @throws Exception 加载可能会抛异常
     */
    List<V> load(K key) throws Exception;
    
}
