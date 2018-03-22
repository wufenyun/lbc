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
public class SimpleQueryingCollection<V> implements QueryingCollection<V> {

	private List<V> data;
	
	public long size() {
		return data.size();
	}

	public Collection<V> all() {
		return data;
	}

	public V get() {
		return null;
	}

	@Override
	public void wrap(List<V> v) {
		this.data = v;
	}

    @Override
    public List<V> query(Query query) {
        if(null == query) {
            return data.stream().collect(Collectors.toList());
        }
        return data.stream().filter((v)->query.predict(v)).collect(Collectors.toList());
    }

    @Override
    public <K> ConcurrentMap<K, V> asMap() {
        return null;
    }

    @Override
    public List<V> data() {
        return data;
    }

}
