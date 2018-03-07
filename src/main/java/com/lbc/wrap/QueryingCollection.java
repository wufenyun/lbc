package com.lbc.wrap;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

import com.lbc.query.Queriable;

public interface QueryingCollection<K, V> extends Queriable<V> {
	
	void wrap(Collection<V> v);
	
	ConcurrentMap<K, V> asMap();
}
