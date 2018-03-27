package com.lbc.wrap;

import java.util.List;
import java.util.Map;

import com.lbc.query.Queriable;

/**
 * 可查询集合对象，提供对内存中集合查询功能
 * 
 * @author wufenyun
 * @param <V>
 * @date 2018年3月3日 上午10:53:32
 */
public interface QueryingCollection<V> extends Queriable<V> {
	
	/** 
	 * 封装集合对象
	 * @param v
	 */
	void wrap(List<V> v);
	
	/** 
	 * 以指定属性为key,key值要求唯一,返回Map
	 * @param keyFieldName 作为key的属性名，一定要是值唯一的属性
	 * @return
	 */
	<K> Map<K, V> asMap(String keyFieldName);
	
	/** 
	 * 返回原始数据集
	 * @return
	 */
	List<V> all();
	
	/** 
	 * 集合记录总数
	 * @return
	 */
	long size();
}
