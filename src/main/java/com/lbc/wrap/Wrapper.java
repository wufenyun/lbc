package com.lbc.wrap;

import java.util.Collection;

import com.lbc.criteria.Criteria;

public interface Wrapper<V> extends BV<V>,Criteria {
	
	void wrap(Collection<V> v);
}
