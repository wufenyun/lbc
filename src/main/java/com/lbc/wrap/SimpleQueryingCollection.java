/**
 * 
 */
package com.lbc.wrap;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.lbc.query.Query;

/**
 * @Description: 
 * @author wufenyun
 * @param <V>
 * @date 2018年3月3日 上午10:53:32
 */

public class SimpleQueryingCollection<K, V> implements QueryingCollection<K, V> {

	private Collection<V> data;
	
	public long size() {
		return 0;
	}

	public Collection<V> all() {
		return data;
	}

	public V get() {
		return null;
	}

	@Override
	public void wrap(Collection<V> v) {
		setData(v);
	}

	public Collection<V> getData() {
		return data;
	}

	public void setData(Collection<V> data) {
		this.data = data;
	}

    @Override
    public List<V> query(Query query) {
        return data.stream().filter((v)->query.predict(v)).collect(Collectors.toList());
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        // TODO Auto-generated method stub
        return null;
    }

}
