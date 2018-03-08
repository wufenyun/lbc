package com.lbc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lbc.exchanger.CacheExchanger;
import com.lbc.wrap.QueryingCollection;
import com.lbc.wrap.SimpleQueryingCollection;


public class LocalCache implements Cache {

    private Map<Object, QueryingCollection<?, ?>> storage = new ConcurrentHashMap<>();
    private Map<Object, CacheExchanger<?,?>> initialedCache = new ConcurrentHashMap<>();
    private Map<Class<? extends CacheExchanger<?, ?>>, CacheExchanger> exchangerMapping = new ConcurrentHashMap<>();
    
    public void regist(Object key, CacheExchanger<?,?> exchanger) {
        initialedCache.put(key, exchanger);
    }
    
    @Override
    public <K, V> QueryingCollection<K, V> get(K key) {
        return (QueryingCollection<K, V>) storage.get(key);
    }
    
    @Override
    public <K, V> QueryingCollection<K, V> get(K key,Class<? extends CacheExchanger> clazz) {
        QueryingCollection<?, ?> value = storage.get(key);
        synchronized (key) {
            if(null == value) {
            	Collection<?> data;
                try {
                    data = exchangerMapping.get(clazz).load(key);
                    this.put(key,data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return (QueryingCollection<K, V>) storage.get(key);
    }
    
    @Override
    public <K> void refresh(K k) {
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, CacheExchanger<?,?>> getInitialedCache() {
        return initialedCache;
    }

    public void setInitialedCache(Map<Object, CacheExchanger<?,?>> initialedCache) {
        this.initialedCache = initialedCache;
    }

    @Override
    public <K,V> void put(K key, Collection<V> value) {
        QueryingCollection<K,V> wrapper = new SimpleQueryingCollection<>();
        wrapper.wrap(value);
        storage.put(key, wrapper);
    }


}
