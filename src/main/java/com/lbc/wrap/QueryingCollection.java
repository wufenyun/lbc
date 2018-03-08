package com.lbc.wrap;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import com.lbc.query.Queriable;

/**
 * @Description: 
 * @author wufenyun
 * @param <V>
 * @date 2018年3月3日 上午10:53:32
 */
public interface QueryingCollection<K, V> extends Queriable<V> {
	
	void wrap(Collection<V> v);
	
	ConcurrentMap<K, V> asMap();
	
	Collection<V> data();
}
