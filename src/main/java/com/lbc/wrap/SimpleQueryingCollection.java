/**
 * 
 */
package com.lbc.wrap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lbc.query.Query;
import com.lbc.support.AssertUtil;
import com.lbc.support.PropertyUtil;

/**
 * See QueryingCollection
 * 
 * @author wufenyun
 * @param <V>
 * @date 2018年3月3日 上午10:53:32
 */
public class SimpleQueryingCollection<V> implements QueryingCollection<V> {

	private List<V> data;
	
	@Override
	public long size() {
		return data.size();
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

    @SuppressWarnings("unchecked")
    @Override
    public <K> Map<K, V> asMap(String keyFieldName) {
        AssertUtil.notBlank(keyFieldName, "属性字段名不能为空");
        return (Map<K, V>) data.stream().collect(Collectors.toMap(x -> {
            return PropertyUtil.getFieldValue(x, keyFieldName);
        }, y -> y));
    }

    @Override
    public List<V> all() {
        return data;
    }

}
