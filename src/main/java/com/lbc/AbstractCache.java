package com.lbc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AbstractCache<K, V> implements Cache<K, V> {

    private Map<K, V> cache = new ConcurrentHashMap<>();
    
    @Override
    public V get(K key, CacheLoader<K, V> loader) {
        V value = cache.get(key);
        synchronized (key) {
            if(null == value) {
                loader.load(key);
            }
        }
        return value;
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void refresh(K k) {
        
    }


}
