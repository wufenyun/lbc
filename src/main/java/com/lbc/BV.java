/**
 * Package: com.lbc
 * Description: 
 */
package com.lbc;

import java.util.Collection;

/**
 * Description:  BatchV
 * Date: 2018年3月2日 下午3:14:12
 * @author wufenyun 
 */
public interface BV<V> {
    
    long size();
    
    Collection<V> all();
    
    V get();
}
