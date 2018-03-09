/**
 * Package: com.lbc.refresh
 * Description: 
 */
package com.lbc.refresh;

/**
 * Description:  
 * Date: 2018年3月9日 上午9:51:37
 * @author wufenyun 
 */
public interface Refresher {
    
    <K> void refresh(K key);
    
    void refreshAll();
}
