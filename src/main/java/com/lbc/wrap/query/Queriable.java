/**
 * 
 */
package com.lbc.wrap.query;

import java.util.List;

/**
 * 提供本地缓存查询功能
 * 
 * @author wufenyun
 * @date 2018年3月3日 上午10:33:32
 */
public interface Queriable<V> {
	
	/** 
	 * 查询
	 * @param query
	 * @return
	 */
	List<V> query(Query query);
}
