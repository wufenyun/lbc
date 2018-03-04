package com.lbc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AbstractCache<K, V> implements Cache<K, V> {

    private Map<K, Wrapper<V>> cache = new ConcurrentHashMap<>();
    private Map<K, CacheLoader<K, V>> loaderCache = new ConcurrentHashMap<>();
    
    public AbstractCache(K key,CacheLoader<K, V> loader) {
    	loaderCache.put(key, loader);
    }
    
    @Override
    public Wrapper<V> get(K key, CacheLoader<K, V> loader) {
    	Wrapper<V> value = cache.get(key);
        synchronized (key) {
            if(null == value) {
            	Collection<V> data = loader.load(key);
            	this.put(key, data);
            }
        }
        return cache.get(key);
    }
    
    private CacheLoader<K, V> getLoader(K key) {
    	return loaderCache.get(key);
    }

    @Override
    public void refresh(K k) {
        
    }

	@Override
	public void put(K k, Collection<V> value) {
		SimpleWrapper<V> wrapper = new SimpleWrapper<>();
    	wrapper.wrap(value);
    	cache.put(k, wrapper);
	}


}
