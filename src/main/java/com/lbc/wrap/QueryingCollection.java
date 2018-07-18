package com.lbc.wrap;

import com.lbc.wrap.query.Queriable;
import com.lbc.wrap.tree.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * 可查询集合对象，提供对内存中集合查询功能
 * 
 * @author wufenyun
 * @param <V>
 */
public interface QueryingCollection<V> extends Queriable<V> {
	
	/**
	 * 封装集合对象
	 *
	 * @param v
	 * @return
	 */
	QueryingCollection<V> wrap(List<V> v);

	/**
	 * 集合记录总数
	 * @return
	 */
	long size();

	/** 
	 * 返回原始数据集
	 * @return
	 */
	List<V> all();

	/**
	 * 以指定属性为key,key值要求唯一,返回Map。
	 * 注意返回的集合将不再维护其数据的一致性
	 *
	 * @param keyFieldName 作为key的属性名，一定要是值唯一的属性
	 * @return 返回线程安全的ConcurrentMap
	 */
	<K> Map<K, V> asMap(String keyFieldName);

	/**
	 * 将集合数据生成树的形式返回，此接口通常用于支持树结构的字典数据，比如类目、地域信息等。
	 * 注意返回的集合将不再维护其数据的一致性
	 *
	 * @return 一颗颗树的集合
	 */
	List<TreeNode<V>> generateTrees();
}
