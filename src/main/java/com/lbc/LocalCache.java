package com.lbc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.config.CacheConfiguration;
import com.lbc.exchanger.CacheExchanger;
import com.lbc.support.AssertUtil;
import com.lbc.wrap.QueryingCollection;
import com.lbc.wrap.SimpleQueryingCollection;


/**
 * see Cache
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public class LocalCache implements Cache {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheContext.class);
    
    private LruSupport lruLinkedSupport;
    private boolean isLruCache;
    private CacheConfiguration config;
    private Map<Object, QueryingCollection<?>> storage = new ConcurrentHashMap<>();
    private Map<Object, CacheExchanger<?,?>> initialedKeyMap = new ConcurrentHashMap<>();
    private Map<Object, CacheExchanger<?,?>> allKeyMap = new ConcurrentHashMap<>();
    private Map<Class<?>, CacheExchanger<?, ?>> exchangerMapping = new ConcurrentHashMap<>();
    
    /** 
     * 初始化
     * @param context
     */
    public void init(CacheContext context) {
        this.config = context.getConfiguration();
        if(config.getCacheSizeThreshold() > 0) {
            isLruCache = true;
            lruLinkedSupport = new LruSupport(config.getCacheSizeThreshold());
        }
    }
    
    /** 
     * 注册缓存加载器信息
     * @param key
     * @param exchanger
     */
    public void regist(Object key, CacheExchanger<?,?> exchanger) {
        initialedKeyMap.put(key, exchanger);
    }
    
    /** 
     * 注册缓存加载器信息
     * @param clazz
     * @param exchanger
     */
    public void registExchangerMapping(Class<?> clazz, CacheExchanger<?, ?> exchanger) {
        exchangerMapping.put(clazz, exchanger);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <K, V> QueryingCollection<V> get(K key) {
        AssertUtil.notNull(key, "key不能为空");
        QueryingCollection<V> collection = (QueryingCollection<V>) storage.get(key);
        
        //如果为空，直接返回
        if(null==collection) {
            return collection;
        }
        
        afterNodeGet(key);
        return collection;
    }
    
    
    /** 
     * 获取节点之后的操作，这里进行lru
     * @param key
     */
    private void afterNodeGet(Object key) {
        //如果不需要lru处理，直接返回
        if((!isLruCache) || initialedKeyMap.containsKey(key)) {
            return;
        }
        
        synchronized(key) {
            Object evictKey = lruLinkedSupport.getNeedReclaimedKey(key);
            if(null != evictKey) {
                storage.remove(evictKey);
                allKeyMap.remove(evictKey);
                logger.info("从缓存里移除LRU缓存,key={}",evictKey);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <K, V> QueryingCollection<V> get(K key,Class<? extends CacheExchanger<K, V>> clazz) {
        AssertUtil.notNull(key, "key不能为空");
        
        QueryingCollection<?> value = storage.get(key);
        synchronized (key) {
            if(null == value) {
                List<V> data;
                try {
                    CacheExchanger<K, V> exchanger = (CacheExchanger<K, V>) exchangerMapping.get(clazz);
                    if(null == exchanger) {
                        logger.error("无法通过{}找到其实例对象，请确保相应CacheExchanger已经实例化",clazz);
                        return null;
                    }
                    data = exchanger.load(key);
                    //如果为空，直接返回,这里需要考虑缓存穿透
                    if(null==data) {
                        return null;
                    }
                    this.put(key,data,exchanger);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        afterNodeGet(key);
        return (QueryingCollection<V>) storage.get(key);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <K, V> QueryingCollection<V> refresh(K key) {
        AssertUtil.notNull(key, "key不能为空");
        CacheExchanger exchanger = allKeyMap.get(key);
        if(null == exchanger) {
            throw new IllegalArgumentException("无法通过key:"+key+"找到其缓存加载器，请确保相应key是正确的");
        }
        
        List<V> data;
        try {
            data = exchanger.load(key);
            QueryingCollection<V> wrapper = new SimpleQueryingCollection<>();
            wrapper.wrap(data);
            return (QueryingCollection<V>) storage.replace(key, wrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <K,V> void put(K key, List<V> data,CacheExchanger<K, V> exchanger) {
        QueryingCollection<V> wrapper = new SimpleQueryingCollection<>();
        wrapper.wrap(data);
        storage.put(key, wrapper);
        allKeyMap.put(key, exchanger);
    }

    @Override
    public <K, V> void replace(K key, List<V> value) {
        QueryingCollection<V> wrapper = new SimpleQueryingCollection<>();
        wrapper.wrap(value);
        storage.replace(key, wrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, CacheExchanger<?,?>> getInitialedKeyMap() {
        return initialedKeyMap;
    }

    public void setInitialedCache(Map<Object, CacheExchanger<?,?>> initialedCache) {
        this.initialedKeyMap = initialedCache;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, CacheExchanger<?,?>> getAllKeyMap() {
        return allKeyMap;
    }

    
}
