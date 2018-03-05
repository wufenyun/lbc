package com.lbc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lbc.cacheloader.CacheLoader;
import com.lbc.wrap.SimpleWrapper;
import com.lbc.wrap.Wrapper;


public class DefaultCache<K, V> implements Cache<K, V> {

    private Map<K, Wrapper<V>> cache = new ConcurrentHashMap<>();
    private Map<K, CacheLoader<K, V>> loaderCache = new ConcurrentHashMap<>();
    
    @Override
    public Wrapper<V> get(K key, CacheLoader<K, V> loader) {
    	Wrapper<V> value = cache.get(key);
        synchronized (key) {
            if(null == value) {
            	Collection<V> data;
                try {
                    data = loader.load(key);
                    this.put(key, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return cache.get(key);
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
