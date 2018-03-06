/**
 * 
 */
package com.lbc.criteria;

import java.util.List;

/**
 * @Description: 
 * @author wufenyun
 * @date 2018年3月3日 上午10:33:32
 */

public interface Queriable<V> {
	
	List<V> query(Criteria<V> criteria);
}
