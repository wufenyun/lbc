package com.lbc.wrap;

import java.util.Collection;

import com.lbc.criteria.Queriable;

public interface QueryingCollection<V> extends BV<V>,Queriable<V> {
	
	void wrap(Collection<V> v);
	
	
}
