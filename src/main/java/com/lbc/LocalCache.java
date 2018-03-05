/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import com.lbc.cacheloader.CacheLoader;
import com.lbc.wrap.BV;

/**
 * Description:  
 * Date: 2018年3月2日 下午3:25:20
 * @author wufenyun 
 */
public class LocalCache<K, V extends BV> extends DefaultCache<K, BV>{

	public LocalCache(K key, CacheLoader<K, BV> loader) {
		//super(key, loader);
	}
    
	
}
