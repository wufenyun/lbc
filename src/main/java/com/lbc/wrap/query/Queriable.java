/**
 * 
 */
package com.lbc.wrap.query;

import java.util.List;

/**
 * 提供本地缓存查询功能
 * 
 * @author wufenyun
 */
public interface Queriable<V> {

	/**
	 * 查询,返回单个对象
	 *
	 * @param query
	 * @return
	 */
	V queryUnique(Query query);
	/** 
	 * 查询,返回List
	 *
	 * @param query
	 * @return
	 */
	List<V> query(Query query);

}
