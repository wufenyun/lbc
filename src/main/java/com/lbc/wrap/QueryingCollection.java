package com.lbc.wrap;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

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
	 * 以Map集合
	 * @return
	 */
	<K> ConcurrentMap<K, V> asMap();
	
	/** 
	 * 返回原始数据集
	 * @return
	 */
	List<V> data();
}
