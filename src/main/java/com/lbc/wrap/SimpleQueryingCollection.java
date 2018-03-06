/**
 * 
 */
package com.lbc.wrap;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.lbc.criteria.Criteria;

/**
 * @Description: 
 * @author wufenyun
 * @param <V>
 * @date 2018年3月3日 上午10:53:32
 */

public class SimpleQueryingCollection<V> implements QueryingCollection<V> {

	private Collection<V> data;
	
	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<V> all() {
		return data;
	}

	@Override
	public V get() {
		// TODO Auto-generated method stub
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
    public List<V> query(Criteria<V> criteria) {
        
        return data.stream().filter((v)->v.equals("a")).collect(Collectors.toList());
    }

}
