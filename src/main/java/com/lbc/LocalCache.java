package com.lbc;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lbc.exchanger.CacheExchanger;
import com.lbc.wrap.QueryingCollection;
import com.lbc.wrap.SimpleQueryingCollection;


/**
 * Description:  
 * Date: 2018年3月2日 下午2:38:28
 * @author wufenyun 
 */
public class LocalCache implements Cache {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultCacheContext.class);
    
    private Map<Object, QueryingCollection<?>> storage = new ConcurrentHashMap<>();
    private Map<Object, CacheExchanger<?,?>> initialedKeyMap = new ConcurrentHashMap<>();
    private Map<Object, CacheExchanger<?,?>> allKeyMap = new ConcurrentHashMap<>();
    private Map<Class<?>, CacheExchanger<?, ?>> exchangerMapping = new ConcurrentHashMap<>();
    private CacheContext context;

    public void regist(Object key, CacheExchanger<?,?> exchanger) {
        initialedKeyMap.put(key, exchanger);
    }
    
    public void registExchangerMapping(Class<?> clazz, CacheExchanger<?, ?> exchanger) {
        exchangerMapping.put(clazz, exchanger);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <K, V> QueryingCollection<V> get(K key) {
        return (QueryingCollection<V>) storage.get(key);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <K, V> QueryingCollection<V> get(K key,Class<? extends CacheExchanger<K, V>> clazz) {
        QueryingCollection<V> value = (QueryingCollection<V>) storage.get(key);
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
                    this.put(key,data,exchanger);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return (QueryingCollection<V>) storage.get(key);
    }
    
    @Override
    public <K> void refresh(K k) {
        
    }

    @Override
    public <K,V> void put(K key, List<V> data,CacheExchanger<K, V> exchanger) {
        QueryingCollection<V> wrapper = new SimpleQueryingCollection<>();
        wrapper.wrap(data);
        storage.put(key, wrapper);
        allKeyMap.put(key, exchanger);
        //context.
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

    public CacheContext getContext() {
        return context;
    }

    public void setContext(CacheContext context) {
        this.context = context;
    }


}
