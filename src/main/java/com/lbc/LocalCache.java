package com.lbc;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lbc.exchanger.CacheExchanger;
import com.lbc.wrap.QueryingCollection;
import com.lbc.wrap.SimpleQueryingCollection;


public class LocalCache<K, V> implements Cache<K, V> {

    private Map<K, QueryingCollection<K, V>> cache = new ConcurrentHashMap<>();
    private Map<K, CacheExchanger<K, V>> initialedCache = new ConcurrentHashMap<>();
    
    @Override
    public QueryingCollection<K, V> get(K key, CacheExchanger<K, V> loader) {
        QueryingCollection<K, V> value = cache.get(key);
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
		SimpleQueryingCollection<K, V> wrapper = new SimpleQueryingCollection<>();
    	wrapper.wrap(value);
    	cache.put(k, wrapper);
	}

    @Override
    public Map<K, CacheExchanger<K, V>> getInitialedCache() {
        // TODO Auto-generated method stub
        return null;
    }

   


}
