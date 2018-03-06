package com.lbc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lbc.cacheloader.CacheLoader;
import com.lbc.wrap.QueryingCollection;
import com.lbc.wrap.SimpleQueryingCollection;


public class DefaultCache<K, V> implements Cache<K, V> {

    private Map<K, QueryingCollection<V>> cache = new ConcurrentHashMap<>();
    private Map<K, CacheLoader<K, V>> loaderCache = new ConcurrentHashMap<>();
    
    @Override
    public QueryingCollection<V> get(K key, CacheLoader<K, V> loader) {
        QueryingCollection<V> value = cache.get(key);
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
		SimpleQueryingCollection<V> wrapper = new SimpleQueryingCollection<>();
    	wrapper.wrap(value);
    	cache.put(k, wrapper);
	}


}
