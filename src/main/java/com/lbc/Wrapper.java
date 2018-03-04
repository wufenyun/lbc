package com.lbc;

import java.util.Collection;

public interface Wrapper<V> extends BV<V>,Criteria {
	
	void wrap(Collection<V> v);
}
